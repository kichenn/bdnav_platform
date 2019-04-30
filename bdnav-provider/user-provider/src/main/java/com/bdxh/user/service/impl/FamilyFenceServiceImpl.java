package com.bdxh.user.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.bdxh.common.helper.baidu.yingyan.FenceUtils;
import com.bdxh.common.helper.baidu.yingyan.constant.FenceConstant;
import com.bdxh.common.helper.baidu.yingyan.request.CreateFenceRoundRequest;
import com.bdxh.common.helper.baidu.yingyan.request.CreateNewEntityRequest;
import com.bdxh.common.helper.baidu.yingyan.request.ModifyFenceRoundRequest;
import com.bdxh.common.support.BaseService;
import com.bdxh.common.utils.BeanMapUtils;
import com.bdxh.common.utils.SnowflakeIdWorker;
import com.bdxh.user.dto.AddFamilyFenceDto;
import com.bdxh.user.dto.FamilyFenceQueryDto;
import com.bdxh.user.dto.UpdateFamilyFenceDto;
import com.bdxh.user.entity.FamilyFence;
import com.bdxh.user.persistence.FamilyFenceMapper;
import com.bdxh.user.service.FamilyFenceService;
import com.bdxh.user.vo.FamilyFenceVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @description: 家长围栏service实现
 * @author: xuyuan
 * @create: 2019-04-10 16:59
 **/
@Service
public class FamilyFenceServiceImpl extends BaseService<FamilyFence> implements FamilyFenceService {
    @Autowired
    private FamilyFenceMapper familyFenceMapper;

    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateFamilyFenceInfo(UpdateFamilyFenceDto updateFamilyFenceDto) {
        ModifyFenceRoundRequest request = new ModifyFenceRoundRequest();
        request.setAk(FenceConstant.AK);
        request.setService_id(FenceConstant.SERVICE_ID);
        request.setDenoise(updateFamilyFenceDto.getDenoise());
        request.setCoord_type(updateFamilyFenceDto.getCoordType());
        request.setFence_name(updateFamilyFenceDto.getFenceName());
        request.setLatitude(Double.valueOf(updateFamilyFenceDto.getLatitude().toString()));
        request.setLongitude(Double.valueOf(updateFamilyFenceDto.getLongitude().toString()));
        request.setRadius(Double.valueOf(updateFamilyFenceDto.getRadius().toString()));
        request.setMonitored_person(updateFamilyFenceDto.getStudentName());
        String modifyRoundResult = FenceUtils.modifyRoundFence(request);
        JSONObject modifyRoundJson = JSONObject.parseObject(modifyRoundResult);
        if (modifyRoundJson.getInteger("status") != 0) {
            throw new RuntimeException("修改学生围栏失败,状态码" + modifyRoundJson.getInteger("status") + "，原因:" + modifyRoundJson.getString("message"));
        }
        FamilyFence familyFence = BeanMapUtils.map(updateFamilyFenceDto, FamilyFence.class);
        familyFenceMapper.updateFamilyFenceInfo(familyFence);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeFamilyFenceInfo(String schoolCode, String cardNumber, String id) {
        FamilyFence familyFence = familyFenceMapper.getFamilyFenceInfo(schoolCode, cardNumber, id);
        //删除监控对象
        String entityResult = FenceUtils.deleteNewEntity(familyFence.getStudentName());
        JSONObject entityResultJson = JSONObject.parseObject(entityResult);
        if (entityResultJson.getInteger("status") != 0) {
            throw new RuntimeException("删除围栏中监控对象失败,状态码" + entityResultJson.getInteger("status") + "，原因:" + entityResultJson.getString("message"));
        }
        //删除围栏
        String delResult = FenceUtils.deleteRoundFence(familyFence.getFenceId());
        JSONObject delResultJson = JSONObject.parseObject(delResult);
        if (delResultJson.getInteger("status") != 0) {
            throw new RuntimeException("删除围栏失败,状态码" + delResultJson.getInteger("status") + "，原因:" + delResultJson.getString("message"));
        }
        familyFenceMapper.removeFamilyFenceInfo(schoolCode, cardNumber, id);
    }

    @Override
    public PageInfo<FamilyFenceVo> getFamilyFenceInfos(FamilyFenceQueryDto familyFenceQueryDto) {
        PageHelper.startPage(familyFenceQueryDto.getPageNum(), familyFenceQueryDto.getPageSize());
        FamilyFence familyFence = BeanMapUtils.map(familyFenceQueryDto, FamilyFence.class);
        List<FamilyFenceVo> listFamilyFence = familyFenceMapper.getFamilyFenceInfos(familyFence);
        PageInfo<FamilyFenceVo> pageInfoFamily = new PageInfo<>(listFamilyFence);
        return pageInfoFamily;
    }

    @Override
    public FamilyFence getFamilyFenceInfo(String schoolCode, String cardNumber, String id) {
        return familyFenceMapper.getFamilyFenceInfo(schoolCode, cardNumber, id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addFamilyFenceInfo(AddFamilyFenceDto addFamilyFenceDto) {
        //创建家长监控对象
        CreateNewEntityRequest entityRequest = new CreateNewEntityRequest();
        entityRequest.setAk(FenceConstant.AK);
        entityRequest.setService_id(FenceConstant.SERVICE_ID);
        entityRequest.setEntity_name(addFamilyFenceDto.getStudentName());
        entityRequest.setEntity_desc("创建单个学生监控对象");
        String entityResult = FenceUtils.createNewEntity(entityRequest);
        JSONObject entityJson = JSONObject.parseObject(entityResult);
        if (entityJson.getInteger("status") != 0) {
            throw new RuntimeException("增加监控终端实体失败,名称：" + addFamilyFenceDto.getStudentName() + "，失败,状态码" + entityJson.getInteger("status") + "，原因:" + entityJson.getString("message"));
        }
        //创建围栏
        CreateFenceRoundRequest fenceRoundRequest = new CreateFenceRoundRequest();
        fenceRoundRequest.setAk(FenceConstant.AK);
        fenceRoundRequest.setService_id(FenceConstant.SERVICE_ID);
        fenceRoundRequest.setDenoise(addFamilyFenceDto.getDenoise());
        fenceRoundRequest.setCoord_type(addFamilyFenceDto.getCoordType());
        fenceRoundRequest.setFence_name(addFamilyFenceDto.getFenceName());
        fenceRoundRequest.setLatitude(Double.valueOf(addFamilyFenceDto.getLatitude().toString()));
        fenceRoundRequest.setLongitude(Double.valueOf(addFamilyFenceDto.getLongitude().toString()));
        fenceRoundRequest.setRadius(Double.valueOf(addFamilyFenceDto.getRadius().toString()));
        fenceRoundRequest.setMonitored_person(addFamilyFenceDto.getStudentName());
        String createRoundResult = FenceUtils.createRoundFence(fenceRoundRequest);
        JSONObject createRoundJson = JSONObject.parseObject(createRoundResult);
        if (createRoundJson.getInteger("status") != 0) {
            throw new RuntimeException("生成围栏失败,状态码" + createRoundJson.getInteger("status") + "，原因:" + createRoundJson.getString("message"));
        }
        addFamilyFenceDto.setFenceId(createRoundJson.getInteger("fence_id"));
        FamilyFence familyFence = BeanMapUtils.map(addFamilyFenceDto, FamilyFence.class);
        familyFence.setId(snowflakeIdWorker.nextId());
        familyFenceMapper.insert(familyFence);
    }
}
