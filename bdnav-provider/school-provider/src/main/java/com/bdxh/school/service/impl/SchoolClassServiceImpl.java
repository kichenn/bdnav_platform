package com.bdxh.school.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.bdxh.common.base.constant.RocketMqConstrants;
import com.bdxh.common.helper.tree.utils.LongUtils;
import com.bdxh.common.support.BaseService;
import com.bdxh.school.dto.SchoolClassDto;
import com.bdxh.school.dto.SchoolClassModifyDto;
import com.bdxh.school.entity.SchoolClass;
import com.bdxh.school.persistence.SchoolClassMapper;
import com.bdxh.school.service.SchoolClassService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.Data;

import java.nio.charset.Charset;
import java.util.*;

/**
 * @Description: 学校院系关系Service
 * @Author: Kang
 * @Date: 2019/2/26 17:58
 */
@Service
@Slf4j
public class SchoolClassServiceImpl extends BaseService<SchoolClass> implements SchoolClassService {

    @Autowired
    private SchoolClassMapper schoolClassMapper;

    @Autowired
    private TransactionMQProducer transactionMQProducer;

    //增加院校关系
    @Override
    public Boolean addSchoolClass(SchoolClassDto schoolClassDto) {
        SchoolClass schoolClass = new SchoolClass();
        BeanUtils.copyProperties(schoolClassDto, schoolClass);
        if (new Long("-1").equals(schoolClass.getParentId())) {
            schoolClass.setParentNames("");
            schoolClass.setThisUrl(schoolClass.getName());
            schoolClass.setParentIds("");
        } else {
            //查询父亲节点
            SchoolClass schoolClassTemp = findSchoolClassById(schoolClassDto.getParentId()).orElse(new SchoolClass());
            //树状
            schoolClass.setParentNames(schoolClassTemp.getParentNames() + "/" + schoolClassTemp.getName());
            schoolClass.setThisUrl(schoolClassTemp.getParentNames() + "/" + schoolClassTemp.getName() + "/" + schoolClass.getName());
            schoolClass.setParentIds(schoolClassTemp.getParentIds() + "," + schoolClassTemp.getId());
        }
        Boolean result = schoolClassMapper.insertSelective(schoolClass) > 0;
        return result;
    }

    //修改院校关系
    @Override
    public Boolean modifySchoolClass(SchoolClassModifyDto schoolClassDto) {
        try {
            SchoolClass schoolClass = new SchoolClass();
            BeanUtils.copyProperties(schoolClassDto, schoolClass);
            if (new Long("-1").equals(schoolClass.getParentId())) {
                schoolClass.setParentNames("");
                schoolClass.setThisUrl(schoolClass.getName());
                schoolClass.setParentIds("");
            } else {
                //查询父亲节点
                SchoolClass schoolClassTemp = findSchoolClassById(schoolClassDto.getParentId()).orElse(new SchoolClass());
                //树状
                schoolClass.setParentNames(schoolClassTemp.getParentNames() + "/" + schoolClassTemp.getName());
                schoolClass.setThisUrl(schoolClassTemp.getParentNames() + "/" + schoolClassTemp.getName() + "/" + schoolClass.getName());
                schoolClass.setParentIds(schoolClassTemp.getParentIds() + "," + schoolClassTemp.getId());
            }
            //查询当前节点的子节点
            // 修改当前组织，  子部门组织的 url parentnames 要跟着修改
            List<SchoolClass> depts = findSchoolByParentId(schoolClass.getId());
            if (CollectionUtils.isNotEmpty(depts)) {
                depts.forEach(e -> {
                    e.setParentNames(schoolClass.getParentNames() + "/" + schoolClass.getName());
                    e.setThisUrl(schoolClass.getParentNames() + "/" + schoolClass.getName() + "/" + e.getName());
                    schoolClassMapper.updateByPrimaryKeySelective(e);
                });
            }
            Boolean result = schoolClassMapper.updateByPrimaryKeySelective(schoolClass) > 0;
            log.info("修改院系组织架构完成");
            if (result) {
                //院系修改成功之后，发送异步消息，通知user服务，学校院系组织架构有变动，
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("data", schoolClass);
                jsonObject.put("message", "学校院系组织架构有调整");
                Message message1 = new Message(RocketMqConstrants.Topic.schoolOrganizationTopic, RocketMqConstrants.Tags.schoolOrganizationTag_class, jsonObject.toJSONString().getBytes(Charset.forName("utf-8")));
                transactionMQProducer.sendMessageInTransaction(message1, null);
                log.info("发送院系组织架构通知完成");
            }
            return result;
            } catch(Exception e){
                e.printStackTrace();
                log.error("发送学校院系组织更新消息失败");
                return  false;
            }
    }


    //id删除院校关系
    @Override
    public Boolean delSchoolClassById(Long id) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("tableName", "t_school_class");
        List<Map<String,String>> data=new ArrayList<>();
        Map<String,String> map=new HashMap<>();
        map.put("id",id.toString());
        data.add(map);
        jsonObject.put("data", data);
        jsonObject.put("delFlag",1);
        Message message = new Message(RocketMqConstrants.Topic.bdxhTopic,RocketMqConstrants.Tags.schoolOrganizationTag_class, jsonObject.toJSONString().getBytes(Charset.forName("utf-8")));
        try {
            transactionMQProducer.send(message);
        }catch (Exception e){
            e.printStackTrace();
            log.info("消息推送MQ失败");
        }
        return schoolClassMapper.deleteByPrimaryKey(id) > 0;
    }

    //id批量删除院校关系
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean batchDelSchoolClassInIds(List<Long> ids) {
        //循环拼接数据结构然后推送给第三方
        List<Map<String,String>> data=new ArrayList<>();
        Map<String,String> map=new HashMap<>();
        for (Long id : ids) {
            map.put("id",id.toString());
            data.add(map);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("tableName", "t_school_class");
        jsonObject.put("data", data);
        jsonObject.put("delFlag",1);
        Message message = new Message(RocketMqConstrants.Topic.bdxhTopic,RocketMqConstrants.Tags.schoolOrganizationTag_class, jsonObject.toJSONString().getBytes(Charset.forName("utf-8")));
        try {
            transactionMQProducer.send(message);
        }catch (Exception e){
            e.printStackTrace();
            log.info("消息推送MQ失败");
        }
        return schoolClassMapper.batchDelSchoolClassInIds(ids) > 0;
    }

    //删除院校底下信息
    @Override
    public Boolean delSchoolClassBySchoolId(Long schoolId) {
       SchoolClass schoolClass =new SchoolClass();
       schoolClass.setSchoolId(schoolId);
       List<SchoolClass> schoolClassList=schoolClassMapper.select(schoolClass);
        List<Map<String,String>> data=new ArrayList<>();
        Map<String,String> map=new HashMap<>();
        for (SchoolClass aClass : schoolClassList) {
            map.put("id",aClass.getId().toString());
            data.add(map);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("tableName", "t_school_class");
        jsonObject.put("data", data);
        jsonObject.put("delFlag",1);
        Message message = new Message(RocketMqConstrants.Topic.bdxhTopic,RocketMqConstrants.Tags.schoolOrganizationTag_class, jsonObject.toJSONString().getBytes(Charset.forName("utf-8")));
        try {
            transactionMQProducer.send(message);
        }catch (Exception e){
            e.printStackTrace();
            log.info("消息推送MQ失败");
        }
        return schoolClassMapper.delSchoolClassBySchoolId(schoolId) > 0;
    }

    //学校id查询等级节点列表
    @Override
    public List<SchoolClass> findSchoolParentClassBySchoolId(Long schoolId) {
        SchoolClass schoolClass = new SchoolClass();
        schoolClass.setSchoolId(schoolId);
        return schoolClassMapper.select(schoolClass);
    }

    //id查询信息
    @Override
    public Optional<SchoolClass> findSchoolClassById(Long id) {
        return Optional.ofNullable(schoolClassMapper.selectByPrimaryKey(id));
    }

    //查询所有信息
    @Override
    public List<SchoolClass> findSchoolClassAll() {
        return schoolClassMapper.selectAll();
    }

    @Override
    public SchoolClass findSchoolClassBySchoolClass(SchoolClass schoolClass) {
        return schoolClassMapper.findSchoolClassBySchoolClass(schoolClass);
    }

    //父id查询院系信息
    @Override
    public List<SchoolClass> findSchoolByParentId(Long parentId) {
        return schoolClassMapper.findSchoolByParentId(parentId);
    }

    @Override
    public List<SchoolClass> queryClassBySchoolCode(String schoolCode) {
        return schoolClassMapper.queryClassBySchoolCode(schoolCode);
    }

}
