package com.bdxh.school.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.bdxh.common.helper.baidu.yingyan.FenceUtils;
import com.bdxh.common.helper.baidu.yingyan.constant.FenceConstant;
import com.bdxh.common.helper.baidu.yingyan.request.CreateFenceRoundRequest;
import com.bdxh.common.helper.baidu.yingyan.request.CreateNewEntityRequest;
import com.bdxh.common.helper.baidu.yingyan.request.ModifyFenceRoundRequest;
import com.bdxh.school.dto.FenceEntityDto;
import com.bdxh.school.dto.SchoolFenceQueryDto;
import com.bdxh.school.entity.School;
import com.bdxh.school.entity.SchoolClass;
import com.bdxh.school.entity.SchoolDept;
import com.bdxh.school.persistence.SchoolClassMapper;
import com.bdxh.school.persistence.SchoolDeptMapper;
import com.bdxh.school.persistence.SchoolMapper;
import com.bdxh.school.service.SchoolFenceService;
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
import com.bdxh.school.entity.SchoolFence;
import com.bdxh.school.persistence.SchoolFenceMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private SchoolClassMapper schoolClassMapper;

    @Autowired
    private SchoolDeptMapper schoolDeptMapper;

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
        request.setMonitored_person(fenceEntityDto.get(0).getId() + "_" + fenceEntityDto.get(0).getName());
        String createRoundResult = FenceUtils.createRoundFence(request);
        JSONObject createRoundJson = JSONObject.parseObject(createRoundResult);
        if (createRoundJson.getInteger("status") != 0) {
            throw new RuntimeException("生成围栏失败,状态码:" + createRoundJson.getInteger("status") + "，原因:" + createRoundJson.getString("message"));
        }
        schoolFence.setFenceId(createRoundJson.getInteger("fence_id"));

        List<FenceEntityDto> modifyFenceEntitys = fenceEntityDto.subList(1, fenceEntityDto.size());
        //将剩余人员添加到围栏里
        if (modifyFenceEntitys.size() > 100) {
            //批量更新监控人员
            int count = modifyFenceEntitys.size() % 100 == 0 ? modifyFenceEntitys.size() / 100 : modifyFenceEntitys.size() / 100 + 1;
            for (int i = 0; i < count; i++) {

            }
        } else {

        }

        return null;
//        return schoolFenceMapper.insertSelective(schoolFence) > 0;
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
    public Boolean modifyFence(SchoolFence schoolFence) throws RuntimeException {
        //部门或者院系名称
        String groupName = "";
        // 用户群类型 1 学生 2 老师
        if (new Byte("1").equals(schoolFence.getGroupType())) {
            SchoolClass schoolClass = schoolClassMapper.selectByPrimaryKey(schoolFence.getGroupId());
            groupName = schoolClass != null ? schoolClass.getName() : "";
        } else if (new Byte("2").equals(schoolFence.getGroupType())) {
            SchoolDept schoolDept = schoolDeptMapper.selectByPrimaryKey(schoolFence.getGroupId());
            groupName = schoolDept != null ? schoolDept.getName() : "";
        }
        ModifyFenceRoundRequest request = new ModifyFenceRoundRequest();
        request.setAk(FenceConstant.AK);
        request.setService_id(FenceConstant.SERVICE_ID);
        request.setDenoise(schoolFence.getDenoise());
        request.setCoord_type(schoolFence.getCoordType());
        request.setFence_name(schoolFence.getName());
        request.setLatitude(Double.valueOf(schoolFence.getLatitude().toString()));
        request.setLongitude(Double.valueOf(schoolFence.getLongitude().toString()));
        request.setRadius(Double.valueOf(schoolFence.getRadius().toString()));
        request.setMonitored_person(groupName);

        String modifyRoundResult = FenceUtils.modifyRoundFence(request);
        JSONObject modifyRoundJson = JSONObject.parseObject(modifyRoundResult);
        if (modifyRoundJson.getInteger("status") != 0) {
            throw new RuntimeException("修改学校围栏失败,状态码" + modifyRoundJson.getInteger("status") + "，原因:" + modifyRoundJson.getString("message"));
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
        //部门或者院系名称
        String groupName = "";
        // 用户群类型 1 学生 2 老师
        if (new Byte("1").equals(schoolFence.getGroupType())) {
            SchoolClass schoolClass = schoolClassMapper.selectByPrimaryKey(schoolFence.getGroupId());
            groupName = schoolClass != null ? schoolClass.getName() : "";
        } else if (new Byte("2").equals(schoolFence.getGroupType())) {
            SchoolDept schoolDept = schoolDeptMapper.selectByPrimaryKey(schoolFence.getGroupId());
            groupName = schoolDept != null ? schoolDept.getName() : "";
        }
        //删除监控对象
        String entityResult = FenceUtils.deleteNewEntity(groupName);
        JSONObject entityResultJson = JSONObject.parseObject(entityResult);
        if (entityResultJson.getInteger("status") != 0) {
            throw new RuntimeException("删除围栏中监控对象失败,状态码" + entityResultJson.getInteger("status") + "，原因:" + entityResultJson.getString("message"));
        }
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
                    SchoolClass schoolClass = schoolClassMapper.selectByPrimaryKey(e.getGroupId());
                    temp.setGroupName(schoolClass != null ? schoolClass.getName() : "");
                } else if (new Byte("2").equals(e.getGroupType())) {
                    SchoolDept schoolDept = schoolDeptMapper.selectByPrimaryKey(e.getGroupId());
                    temp.setGroupName(schoolDept != null ? schoolDept.getName() : "");
                }
                resultFenceShow.add(temp);
            });
        }

        PageInfo pageInfo = new PageInfo<>(resultFenceShow);
        pageInfo.setTotal(page.getTotal());
        return pageInfo;
    }
}
