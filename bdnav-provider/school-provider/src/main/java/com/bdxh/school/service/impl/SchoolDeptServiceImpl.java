package com.bdxh.school.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bdxh.common.base.constant.RocketMqConstrants;
import com.bdxh.common.helper.tree.bean.TreeBean;
import com.bdxh.common.helper.tree.utils.LongUtils;
import com.bdxh.common.helper.tree.utils.TreeLoopUtils;
import com.bdxh.common.support.BaseService;
import com.bdxh.school.dto.SchoolDeptDto;
import com.bdxh.school.dto.SchoolDeptModifyDto;
import com.bdxh.school.entity.School;
import com.bdxh.school.entity.SchoolDept;
import com.bdxh.school.persistence.SchoolDeptMapper;
import com.bdxh.school.persistence.SchoolMapper;
import com.bdxh.school.service.SchoolDeptService;
import com.bdxh.school.vo.SchoolDeptTreeVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.Charset;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description: 学校组织Service
 * @Author: Kang
 * @Date: 2019/2/27 15:05
 */
@Service
@Slf4j
public class SchoolDeptServiceImpl extends BaseService<SchoolDept> implements SchoolDeptService {

    @Autowired
    private SchoolDeptMapper schoolDeptMapper;

    @Autowired
    private SchoolMapper schoolMapper;

    @Autowired
    private TransactionMQProducer transactionMQProducer;

    //增加学校组织
    @Override
    public Boolean addSchoolDept(SchoolDeptDto schoolDeptDto) {
        SchoolDept schoolDept = new SchoolDept();
        BeanUtils.copyProperties(schoolDeptDto, schoolDept);
        if (new Long("-1").equals(schoolDept.getParentId())) {
            schoolDept.setParentNames("");
            schoolDept.setThisUrl(schoolDept.getName());
            schoolDept.setParentIds("");
        } else {
            //查询父亲节点
            SchoolDept schoolDeptTemp = findSchoolDeptById(schoolDeptDto.getParentId()).orElse(new SchoolDept());
            //树状
            schoolDept.setParentNames(schoolDeptTemp.getParentNames() + "/" + schoolDeptTemp.getName());
            schoolDept.setThisUrl(schoolDeptTemp.getParentNames() + "/" + schoolDeptTemp.getName() + "/" + schoolDept.getName());
            schoolDept.setParentIds(schoolDeptTemp.getParentIds() + "," + schoolDeptTemp.getId());
        }
        Boolean result = schoolDeptMapper.insertSelective(schoolDept) > 0;

        //添加判断测试时只推送石齐的数据根据学校ID判断
        if (schoolDeptDto.getSchoolId().equals(Long.parseLong("64"))) {
            if (result) {
                //学校院系组织架构有变动，
                List<SchoolDept> schoolDepts = new ArrayList<>();
                schoolDept.setCreateDate(new Date());
                schoolDept.setUpdateDate(new Date());
                schoolDepts.add(schoolDept);
                //将学校信息转为部门信息的父节点
                School school = schoolMapper.findSchoolBySchoolCode(schoolDept.getSchoolCode());
                SchoolDept schoolDept1 = new SchoolDept();
                schoolDept1.setName(school.getSchoolName());
                schoolDept1.setId(school.getId());
                schoolDept1.setSchoolCode(school.getSchoolCode());
                schoolDept1.setThisUrl(school.getSchoolName());
                schoolDept1.setSort(1);
                schoolDept1.setUpdateDate(school.getUpdateDate());
                schoolDept1.setCreateDate(school.getCreateDate());
                schoolDept1.setSchoolId(school.getId());
                schoolDept1.setParentIds(" ");
                schoolDept1.setParentId(Long.parseLong("0"));
                schoolDept1.setRemark(" ");
                schoolDept1.setParentNames(" ");
                schoolDepts.add(schoolDept1);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("tableName", "t_school_dept");
                jsonObject.put("data", schoolDepts);
                jsonObject.put("delFlag", 0);
                Message message = new Message(RocketMqConstrants.Topic.bdxhTopic, RocketMqConstrants.Tags.schoolOrganizationTag_dept, jsonObject.toJSONString().getBytes(Charset.forName("utf-8")));
                try {
                    transactionMQProducer.send(message);
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("发送学校院系组织新增消息");
                }
            }
        }

        return result;
    }

    //修改学校组织信息
    @Override
    public Boolean modifySchoolDept(SchoolDeptModifyDto schoolDeptDto) {
        try {
            SchoolDept schoolDept = new SchoolDept();
            BeanUtils.copyProperties(schoolDeptDto, schoolDept);
            if (new Long("-1").equals(schoolDept.getParentId())) {
                schoolDept.setParentNames("");
                schoolDept.setThisUrl(schoolDept.getName());
                schoolDept.setParentIds("");
            } else {
                //查询父亲节点
                SchoolDept schoolDeptTemp = findSchoolDeptById(schoolDeptDto.getParentId()).orElse(new SchoolDept());
                //树状
                schoolDept.setParentNames(schoolDeptTemp.getParentNames() + "/" + schoolDeptTemp.getName());
                schoolDept.setThisUrl(schoolDeptTemp.getParentNames() + "/" + schoolDeptTemp.getName() + "/" + schoolDept.getName());
                schoolDept.setParentIds(schoolDeptTemp.getParentIds() + "," + schoolDeptTemp.getId());
            }
            //查询当前节点的子节点
            // 修改当前组织，  子部门组织的 url parentnames 要跟着修改
            List<SchoolDept> depts = findSchoolByParentId(schoolDept.getId());
            if (CollectionUtils.isNotEmpty(depts)) {
                depts.forEach(e -> {
                    e.setParentNames(schoolDept.getParentNames() + "/" + schoolDept.getName());
                    e.setThisUrl(schoolDept.getParentNames() + "/" + schoolDept.getName() + "/" + e.getName());
                    schoolDeptMapper.updateByPrimaryKeySelective(e);
                });
            }
            Boolean result = schoolDeptMapper.updateByPrimaryKeySelective(schoolDept) > 0;

            if (result) {
                //院系修改成功之后，发送异步消息，通知user服务，学校院系组织架构有变动，
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("data", schoolDept);
                jsonObject.put("message", "学校部门组织架构有调整");
                Message message1 = new Message(RocketMqConstrants.Topic.schoolOrganizationTopic, RocketMqConstrants.Tags.schoolOrganizationTag_dept, jsonObject.toJSONString().getBytes(Charset.forName("utf-8")));
                transactionMQProducer.sendMessageInTransaction(message1, null);
                //添加判断测试时只推送石齐的数据根据学校ID判断
                if (schoolDeptDto.getSchoolId().equals(Long.parseLong("64"))) {
                    JSONObject data = jsonObject.getJSONObject("data");
                    List<SchoolDept> schoolDepts = new ArrayList<>();
                    schoolDept.setUpdateDate(new Date());
                    schoolDepts.add(schoolDept);
                    //将学校信息转为部门信息的父节点
                    School school = schoolMapper.findSchoolBySchoolCode(schoolDept.getSchoolCode());
                    SchoolDept schoolDept1 = new SchoolDept();
                    schoolDept1.setName(school.getSchoolName());
                    schoolDept1.setId(school.getId());
                    schoolDept1.setSchoolCode(school.getSchoolCode());
                    schoolDept1.setThisUrl(school.getSchoolName());
                    schoolDept1.setSort(1);
                    schoolDept1.setUpdateDate(school.getUpdateDate());
                    schoolDept1.setCreateDate(school.getCreateDate());
                    schoolDept1.setSchoolId(school.getId());
                    schoolDept1.setParentIds(" ");
                    schoolDept1.setParentId(Long.parseLong("0"));
                    schoolDept1.setRemark(" ");
                    schoolDept1.setParentNames(" ");
                    schoolDepts.add(schoolDept1);
                    //处理数据格式
                    JSONObject jsonObject1 = new JSONObject();
                    jsonObject1.put("tableName", "t_school_dept");
                    jsonObject1.put("data", schoolDepts);
                    jsonObject1.put("delFlag", 0);
                    Message message2 = new Message(RocketMqConstrants.Topic.bdxhTopic, RocketMqConstrants.Tags.schoolOrganizationTag_dept, jsonObject1.toJSONString().getBytes(Charset.forName("utf-8")));
                    transactionMQProducer.send(message2);
                }

            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("发送学校院系组织更新消息");
            return false;
        }
    }

    //删除学校组织信息
    @Override
    public Boolean delSchoolDeptById(Long id) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("tableName", "t_school_dept");
        List<Map<String, String>> data = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        map.put("id", id.toString());
        data.add(map);
        jsonObject.put("data", data);
        jsonObject.put("delFlag", 1);
        Message message = new Message(RocketMqConstrants.Topic.bdxhTopic, RocketMqConstrants.Tags.schoolOrganizationTag_dept, jsonObject.toJSONString().getBytes(Charset.forName("utf-8")));
        try {
            transactionMQProducer.send(message);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("消息推送MQ失败");
        }
        return schoolDeptMapper.deleteByPrimaryKey(id) > 0;
    }

    //批量删除学校组织信息
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean batchDelSchoolDeptInIds(List<Long> ids) {
        //循环拼接数据结构然后推送给第三方
        List<Map<String, String>> data = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        for (Long id : ids) {
            map.put("id", id.toString());
            data.add(map);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("tableName", "t_school_dept");
        jsonObject.put("data", data);
        jsonObject.put("delFlag", 1);
        Message message = new Message(RocketMqConstrants.Topic.bdxhTopic, RocketMqConstrants.Tags.schoolOrganizationTag_dept, jsonObject.toJSONString().getBytes(Charset.forName("utf-8")));
        try {
            transactionMQProducer.send(message);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("消息推送MQ失败");
        }
        return schoolDeptMapper.batchDelSchoolDeptInIds(ids) > 0;
    }

    //删除学校组织信息By SchoolId
    @Override
    public Boolean delSchoolDeptBySchoolId(Long schoolId) {
        //查出要删除的组织信息
        SchoolDept schoolDept = new SchoolDept();
        schoolDept.setSchoolId(schoolId);
        List<SchoolDept> schoolDepts = schoolDeptMapper.select(schoolDept);
        List<Map<String, String>> data = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        //循环拼接数据结构然后推送给第三方
        for (SchoolDept dept : schoolDepts) {
            map.put("id", dept.getId().toString());
            data.add(map);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("tableName", "t_school_dept");
        jsonObject.put("data", data);
        jsonObject.put("delFlag", 1);
        Message message = new Message(RocketMqConstrants.Topic.bdxhTopic, RocketMqConstrants.Tags.schoolOrganizationTag_dept, jsonObject.toJSONString().getBytes(Charset.forName("utf-8")));
        try {
            transactionMQProducer.send(message);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("消息推送MQ失败");
        }
        return schoolDeptMapper.delSchoolDeptBySchoolId(schoolId) > 0;
    }

    //学校id查询等级节点列表（一级节点为父节点）
    @Override
    public List<SchoolDept> findSchoolParentDeptBySchoolId(Long schoolId) {
        SchoolDept schoolDept = new SchoolDept();
        schoolDept.setSchoolId(schoolId);
        return schoolDeptMapper.select(schoolDept);
    }

    //id查询信息
    @Override
    public Optional<SchoolDept> findSchoolDeptById(Long id) {
        return Optional.ofNullable(schoolDeptMapper.selectByPrimaryKey(id));
    }

    //查询所有信息
    @Override
    public List<SchoolDept> findSchoolDeptAll() {
        return schoolDeptMapper.selectAll();
    }

    //父级id查询部门信息
    @Override
    public List<SchoolDept> findSchoolByParentId(Long parentId) {
        return schoolDeptMapper.findSchoolByParentId(parentId);
    }
}
