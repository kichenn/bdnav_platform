package com.bdxh.user.service.impl;

import com.bdxh.common.utils.BeanMapUtils;
import com.bdxh.user.dto.AddFenceAlarmDto;
import com.bdxh.user.dto.FenceAlarmQueryDto;
import com.bdxh.user.dto.UpdateFenceAlarmDto;
import com.bdxh.user.entity.FenceAlarm;
import com.bdxh.user.mongo.FenceAlarmMongo;
import com.bdxh.user.persistence.mongodb.FenceAlarmMongoMapper;
import com.bdxh.user.service.FenceAlarmService;
import com.bdxh.user.vo.FenceAlarmVo;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bdxh.common.support.BaseService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @Description: 业务层实现
 * @Author Kang
 * @Date 2019-04-17 17:29:24
 */
@Service
@Slf4j
public class FenceAlarmServiceImpl extends BaseService<FenceAlarm> implements FenceAlarmService {

    @Autowired
    private FenceAlarmMongoMapper fenceAlarmMongoMapper;


    @Override
    public PageInfo<FenceAlarmVo> getAllFenceAlarmInfos(FenceAlarmQueryDto fenceAlarmQueryDto) {
        return fenceAlarmMongoMapper.getFenceAlarmInfos(fenceAlarmQueryDto);
    }

    @Override
    public FenceAlarmVo getFenceAlarmInfo(String schoolCode, String cardNumber, String id) {
        return fenceAlarmMongoMapper.getFenceAlarmInfo(schoolCode, cardNumber, id);
    }

    @Override
    public void removeFenceAlarmInfo(String id, String schoolCode, String cardNumber) {
        fenceAlarmMongoMapper.removeFenceAlarmInfo(schoolCode, cardNumber, id);
    }

    @Override
    public void batchRemoveFenceAlarmInfo(String ids, String schoolCodes, String cardNumbers) {
        fenceAlarmMongoMapper.batchRemoveFenceAlarmInfo(schoolCodes, cardNumbers, ids);
    }

    @Override
    public void updateFenceAlarmInfo(UpdateFenceAlarmDto updateFenceAlarmDto) {
        FenceAlarmMongo fenceAlarmMongo = BeanMapUtils.map(updateFenceAlarmDto, FenceAlarmMongo.class);
        fenceAlarmMongoMapper.updateFenceAlarmInfo(fenceAlarmMongo);
    }

    @Override
    public void insertFenceAlarmInfo(AddFenceAlarmDto addFenceAlarmDto) {
        FenceAlarmMongo fenceAlarmMongo = BeanMapUtils.map(addFenceAlarmDto, FenceAlarmMongo.class);
        fenceAlarmMongoMapper.insertFenceAlarmInfo(fenceAlarmMongo);
    }

    @Override
    public void updateSchoolName(String schoolCode, String schoolName) {
        fenceAlarmMongoMapper.updateSchoolName(schoolCode, schoolName);
    }

    @Override
    public List<FenceAlarmVo> findFenceAlarmInfos(String schoolCode,String  cardNumber,String fenceId) {
        return fenceAlarmMongoMapper.getFenceAlarmInfos(schoolCode,cardNumber,fenceId);
    }
}
