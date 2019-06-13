package com.bdxh.school.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.bdxh.common.helper.baidu.yingyan.FenceUtils;
import com.bdxh.common.helper.baidu.yingyan.constant.FenceConstant;
import com.bdxh.common.helper.baidu.yingyan.request.CreateFenceRoundRequest;
import com.bdxh.common.helper.baidu.yingyan.request.CreateNewEntityRequest;
import com.bdxh.common.helper.baidu.yingyan.request.ModifyFenceRoundRequest;
import com.bdxh.school.dto.FenceEntityDto;
import com.bdxh.school.dto.SchoolFenceQueryDto;
import com.bdxh.school.entity.*;
import com.bdxh.school.persistence.*;
import com.bdxh.school.service.SchoolFenceService;
import com.bdxh.school.service.SchoolOrgService;
import com.bdxh.school.thread.AddFenceEntityThread;
import com.bdxh.school.vo.SchoolFenceShowVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bdxh.common.support.BaseService;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Description: 业务层实现
 * @Author Kang
 * @Date 2019-04-11 09:56:14
 */
@Service
@Slf4j
public class SchoolFenceServiceImpl extends BaseService<SchoolFence> implements SchoolFenceService {

    @Autowired
    private SchoolFenceMapper schoolFenceMapper;

    @Autowired
    private SchoolMapper schoolMapper;

/*    @Autowired
    private SchoolClassMapper schoolClassMapper;

    @Autowired
    private SchoolDeptMapper schoolDeptMapper;*/

    @Autowired
    private SchoolOrgMapper schoolOrgMapper;

    /*
     *查询总条数
     */
    @Override
    public Integer getSchoolFenceAllCount() {
        return schoolFenceMapper.getSchoolFenceAllCount();
    }

    /*
     *批量删除方法
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean batchDelSchoolFenceInIds(List<Long> ids) {
        return schoolFenceMapper.delSchoolFenceInIds(ids) > 0;
    }

    /**
     * 增加学校围栏
     *
     * @param schoolFence
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean addFence(SchoolFence schoolFence, List<FenceEntityDto> fenceEntityDto) throws RuntimeException {
        //线程池增加监控对象
        AddFenceEntityThread addFenceEntityThread = new AddFenceEntityThread();
        addFenceEntityThread.handleList(fenceEntityDto, 5);
        //增加围栏
        CreateFenceRoundRequest request = new CreateFenceRoundRequest();
        request.setAk(FenceConstant.AK);
        request.setService_id(FenceConstant.SERVICE_ID);
        request.setDenoise(schoolFence.getDenoise());
        request.setCoord_type(schoolFence.getCoordType());
        request.setFence_name(schoolFence.getName());
        request.setLatitude(Double.valueOf(schoolFence.getLatitude().toString()));
        request.setLongitude(Double.valueOf(schoolFence.getLongitude().toString()));
        request.setRadius(Double.valueOf(schoolFence.getRadius().toString()));
        //获取第一个人员创建围栏添加完成
//        request.setMonitored_person(fenceEntityDto.get(0).getId() + "_" + fenceEntityDto.get(0).getName());
        String createRoundResult = FenceUtils.createRoundFence(request);
        JSONObject createRoundJson = JSONObject.parseObject(createRoundResult);
        if (createRoundJson.getInteger("status") == 1) {
            //创建围栏，状态如果为1的情况，为百度那边的服务器内部错误，则再次请求
            createRoundResult = FenceUtils.createRoundFence(request);
            createRoundJson = JSONObject.parseObject(createRoundResult);
        }
        if (createRoundJson.getInteger("status") != 0) {
            throw new RuntimeException("生成围栏失败,状态码:" + createRoundJson.getInteger("status") + "，原因:" + createRoundJson.getString("message"));
        } else {
            log.info("生成围栏成功,状态:{},消息:{}", createRoundJson.getInteger("status"), createRoundJson.getString("message"));
        }
        schoolFence.setFenceId(createRoundJson.getInteger("fence_id"));
        //当监控对象为一人以上，更新监控对象到围栏里
        //将剩余人员添加到围栏里(因为该，接口每次只能接受100人的提交，故计算人次)
        if (fenceEntityDto.size() > 100) {
            //批量更新监控人员
            int count = fenceEntityDto.size() % 100 == 0 ? fenceEntityDto.size() / 100 : fenceEntityDto.size() / 100 + 1;
            for (int i = 0; i < count; i++) {
                int end = (i + 1) * 100;
                //获取需要添加的监控对象信息
                List<FenceEntityDto> tempModifyFenceEntity = fenceEntityDto.subList(i * 100, end > fenceEntityDto.size() ? fenceEntityDto.size() : end);
                List<String> tempFenceEntityNames = tempModifyFenceEntity.stream().map(e -> {
                    return e.getId() + "_" + e.getName();
                }).collect(Collectors.toList());
                //切割监控人员为string
                String finalEntity = String.join(",", tempFenceEntityNames);
                String result = FenceUtils.addMonitoredPerson(schoolFence.getFenceId(), finalEntity);
                JSONObject resultJson = JSONObject.parseObject(result);
                if (resultJson.getInteger("status") != 0) {
                    log.error("更新围栏监控人员失败:{},状态:{}", resultJson.getString("message"), resultJson.getInteger("status"));
                } else {
                    log.info("分批更新围栏监控人员成功,状态:{},消息:{}", createRoundJson.getInteger("status"), createRoundJson.getString("message"));
                }
            }
        } else {
            List<String> fenceEntityNames = fenceEntityDto.stream().map(e -> {
                return e.getId() + "_" + e.getName();
            }).collect(Collectors.toList());
            String finalEntity = String.join(",", fenceEntityNames);
            String result = FenceUtils.addMonitoredPerson(schoolFence.getFenceId(), finalEntity);
            JSONObject resultJson = JSONObject.parseObject(result);
            if (createRoundJson.getInteger("status") == 1) {
                //更新围栏，状态如果为1的情况，为百度那边的服务器内部错误，则再次请求
                createRoundResult = FenceUtils.createRoundFence(request);
                createRoundJson = JSONObject.parseObject(createRoundResult);
            }
            if (resultJson.getInteger("status") != 0) {
                log.error("更新围栏监控人员失败:{},状态:{}", resultJson.getString("message"), resultJson.getInteger("status"));
            } else {
                log.info("更新围栏监控人员成功,状态:{},消息:{}", createRoundJson.getInteger("status"), createRoundJson.getString("message"));
            }
        }
        return schoolFenceMapper.insertSelective(schoolFence) > 0;
    }

    /**
     * 修改学校围栏
     *
     * @param schoolFence
     * @return
     * @throws RuntimeException
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean modifyFence(SchoolFence schoolFence, List<FenceEntityDto> fenceEntityDto) throws RuntimeException {
        //更新学校围栏
        ModifyFenceRoundRequest request = new ModifyFenceRoundRequest();
        request.setAk(FenceConstant.AK);
        request.setService_id(FenceConstant.SERVICE_ID);
        request.setFence_id(schoolFence.getFenceId());
        request.setDenoise(schoolFence.getDenoise());
        request.setCoord_type(schoolFence.getCoordType());
        request.setFence_name(schoolFence.getName());
        request.setLatitude(Double.valueOf(schoolFence.getLatitude().toString()));
        request.setLongitude(Double.valueOf(schoolFence.getLongitude().toString()));
        request.setRadius(Double.valueOf(schoolFence.getRadius().toString()));
        String modifyRoundResult = FenceUtils.modifyRoundFence(request);
        JSONObject modifyRoundJson = JSONObject.parseObject(modifyRoundResult);
        if (modifyRoundJson.getInteger("status") != 0) {
            throw new RuntimeException("修改学校围栏失败,状态码" + modifyRoundJson.getInteger("status") + "，原因:" + modifyRoundJson.getString("message"));
        }
        //当监控对象不为空
        if (CollectionUtils.isNotEmpty(fenceEntityDto)) {
            //线程池：增加监控对象
            AddFenceEntityThread addFenceEntityThread = new AddFenceEntityThread();
            addFenceEntityThread.handleList(fenceEntityDto, 5);
            //清除该围栏的所有以前监控的entity
            String clearResult = FenceUtils.deleteMonitoredPerson(schoolFence.getFenceId(), "#clearentity");
            JSONObject clearJson = JSONObject.parseObject(clearResult);
            if (clearJson.getInteger("status") != 0) {
                log.error("清除该围栏的所有以前监控的entity失败:{},状态:{}", clearJson.getString("message"), clearJson.getInteger("status"));
            } else {
                log.info("清除围栏所有监控对象成功,状态:{},消息:{}", clearJson.getInteger("status"), clearJson.getString("message"));
            }

            //将剩余人员添加到围栏里(因为该，接口每次只能接受100人的提交，故计算人次)
            if (fenceEntityDto.size() > 100) {
                //批量更新监控人员
                int count = fenceEntityDto.size() % 100 == 0 ? fenceEntityDto.size() / 100 : fenceEntityDto.size() / 100 + 1;
                for (int i = 0; i < count; i++) {
                    int end = (i + 1) * 100;
                    //获取需要添加的监控对象信息
                    List<FenceEntityDto> tempModifyFenceEntity = fenceEntityDto.subList(i * 100, end > fenceEntityDto.size() ? fenceEntityDto.size() : end);
                    List<String> tempFenceEntityNames = tempModifyFenceEntity.stream().map(e -> {
                        return e.getId() + "_" + e.getName();
                    }).collect(Collectors.toList());
                    //切割监控人员为string
                    String finalEntity = String.join(",", tempFenceEntityNames);
                    String result = FenceUtils.addMonitoredPerson(schoolFence.getFenceId(), finalEntity);
                    JSONObject resultJson = JSONObject.parseObject(result);
                    if (resultJson.getInteger("status") != 0) {
                        log.error("更新围栏监控人员失败:{},状态:{}", resultJson.getString("message"), resultJson.getInteger("status"));
                    } else {
                        log.info("分批更新围栏监控人员成功,状态:{},消息:{}", resultJson.getInteger("status"), resultJson.getString("message"));
                    }
                }
            } else {
                List<String> fenceEntityNames = fenceEntityDto.stream().map(e -> {
                    return e.getId() + "_" + e.getName();
                }).collect(Collectors.toList());
                String finalEntity = String.join(",", fenceEntityNames);
                String result = FenceUtils.addMonitoredPerson(schoolFence.getFenceId(), finalEntity);
                JSONObject resultJson = JSONObject.parseObject(result);
                if (resultJson.getInteger("status") != 0) {
                    log.error("更新围栏监控人员失败:{},状态:{}", resultJson.getString("message"), resultJson.getInteger("status"));
                } else {
                    log.info("更新围栏监控人员成功,状态:{},消息:{}", resultJson.getInteger("status"), resultJson.getString("message"));
                }
            }
        }

        return schoolFenceMapper.updateByPrimaryKeySelective(schoolFence) > 0;
    }

    /**
     * 删除学校围栏
     *
     * @param id
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean delFence(Long id) throws RuntimeException {
        SchoolFence schoolFence = schoolFenceMapper.selectByPrimaryKey(id);
        if (schoolFence == null) {
            throw new RuntimeException("该学校围栏不存在");
        }
//        //删除监控对象 (删除围栏并不需要删除监控对象，该监控对象可能还在其他围栏)
//        String entityResult = FenceUtils.deleteNewEntity(groupName);
//        JSONObject entityResultJson = JSONObject.parseObject(entityResult);
//        if (entityResultJson.getInteger("status") != 0) {
//            throw new RuntimeException("删除围栏中监控对象失败,状态码" + entityResultJson.getInteger("status") + "，原因:" + entityResultJson.getString("message"));
//        }
        //删除围栏
        String delResult = FenceUtils.deleteRoundFence(schoolFence.getFenceId());
        JSONObject delResultJson = JSONObject.parseObject(delResult);
        if (delResultJson.getInteger("status") != 0) {
            throw new RuntimeException("删除围栏失败,状态码" + delResultJson.getInteger("status") + "，原因:" + delResultJson.getString("message"));
        }
        return schoolFenceMapper.deleteByPrimaryKey(id) > 0;
    }

    /**
     * 围栏分页条件查询
     *
     * @param schoolFenceQueryDto
     * @return
     */
    @Override
    public PageInfo<SchoolFenceShowVo> findFenceInConditionPaging(SchoolFenceQueryDto schoolFenceQueryDto) {

        SchoolFence schoolFence = new SchoolFence();
        BeanUtils.copyProperties(schoolFenceQueryDto, schoolFence);
        //设置状态值
        if (schoolFenceQueryDto.getGroupTypeEnum() != null) {
            schoolFence.setGroupType(schoolFenceQueryDto.getGroupTypeEnum().getKey());
        }
        if (schoolFenceQueryDto.getBlackStatusEnum() != null) {
            schoolFence.setStatus(schoolFenceQueryDto.getBlackStatusEnum().getKey());
        }

        Page page = PageHelper.startPage(schoolFenceQueryDto.getPageNum(), schoolFenceQueryDto.getPageSize());
        List<SchoolFence> schoolFences = schoolFenceMapper.findFenceInConditionPaging(schoolFence);

        List<SchoolFenceShowVo> resultFenceShow = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(schoolFences)) {
            schoolFences.forEach(e -> {
                SchoolFenceShowVo temp = new SchoolFenceShowVo();
                BeanUtils.copyProperties(e, temp);
                School school = schoolMapper.selectByPrimaryKey(temp.getSchoolId());
                temp.setSchoolName(school != null ? school.getSchoolName() : "");
                // 用户群类型 1 学生 2 老师
                if (new Byte("1").equals(e.getGroupType())) {
                    SchoolOrg schoolClass = schoolOrgMapper.selectByPrimaryKey(e.getGroupId());
                    temp.setGroupName(schoolClass != null ? schoolClass.getOrgName() : "");
                } else if (new Byte("2").equals(e.getGroupType())) {
                    SchoolOrg schoolDept = schoolOrgMapper.selectByPrimaryKey(e.getGroupId());
                    temp.setGroupName(schoolDept != null ? schoolDept.getOrgName() : "");
                }
                resultFenceShow.add(temp);
            });
        }

        PageInfo pageInfo = new PageInfo<>(resultFenceShow);
        pageInfo.setTotal(page.getTotal());
        return pageInfo;
    }
}
