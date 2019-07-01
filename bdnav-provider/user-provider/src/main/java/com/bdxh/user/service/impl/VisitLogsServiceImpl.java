package com.bdxh.user.service.impl;

import com.bdxh.common.utils.BeanMapUtils;
import com.bdxh.common.utils.DateUtil;
import com.bdxh.user.dto.AddVisitLogsDto;
import com.bdxh.user.dto.UpdateVisitLogsDto;
import com.bdxh.user.dto.VisitLogsQueryDto;
import com.bdxh.user.entity.VisitLogs;
import com.bdxh.user.enums.FamliyBlackUrlStatusEnum;
import com.bdxh.user.mongo.VisitLogsMongo;
import com.bdxh.user.persistence.mongodb.VisitLogsMongoMapper;
import com.bdxh.user.service.VisitLogsService;
import com.bdxh.user.vo.VisitLogsVo;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.bdxh.common.support.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 业务层实现
 * @Author Kang
 * @Date 2019-04-17 17:29:24
 */
@Service
@Slf4j
public class VisitLogsServiceImpl extends BaseService<VisitLogs> implements VisitLogsService {

    @Autowired
    private VisitLogsMongoMapper visitLogsMongoMapper;


    @Override
    public PageInfo<VisitLogsVo> getVisitLogsInfos(VisitLogsQueryDto visitLogsQueryDto) {
        return visitLogsMongoMapper.getVisitLogsInfos(visitLogsQueryDto);
    }

    @Override
    public VisitLogsVo getVisitLogsInfo(String schoolCode, String cardNumber, String id) {
        return visitLogsMongoMapper.getVisitLogsInfo(schoolCode, cardNumber, id);
    }

    @Override
    public void updateVisitLogsInfo(UpdateVisitLogsDto updateVisitLogsDto) {
        VisitLogsMongo visitLogsMongo = BeanMapUtils.map(updateVisitLogsDto, VisitLogsMongo.class);
        visitLogsMongoMapper.updateVisitLogsInfo(visitLogsMongo);
    }

    @Override
    public void removeVisitLogsInfo(String schoolCode, String cardNumber, String id) {
        visitLogsMongoMapper.removeVisitLogsInfo(schoolCode, cardNumber, id);
    }

    @Override
    public void batchRemoveVisitLogsInfo(String schoolCodes, String cardNumbers, String ids) {
        visitLogsMongoMapper.batchRemoveVisitLogsInfo(schoolCodes, cardNumbers, ids);
    }

    @Override
    public void insertVisitLogsInfo(AddVisitLogsDto addVisitLogsDto) {
        VisitLogsMongo visitLogsMongo = BeanMapUtils.map(addVisitLogsDto, VisitLogsMongo.class);
        visitLogsMongoMapper.insertVisitLogsInfo(visitLogsMongo);
    }

    @Override
    public void updateSchoolName(String schoolCode, String schoolName) {
        visitLogsMongoMapper.updateSchoolName(schoolCode, schoolName);
    }

    @Override
    public PageInfo<VisitLogsVo> queryVisitLogByCardNumber(String schoolCode, String cardNumber) {
        VisitLogsQueryDto visitLogsQueryDto = new VisitLogsQueryDto();
        visitLogsQueryDto.setCardNumber(cardNumber);
        visitLogsQueryDto.setSchoolCode(schoolCode);

        return visitLogsMongoMapper.getVisitLogsInfos(visitLogsQueryDto);
    }

    /**
     * 批量添加浏览网站日志接口
     *
     * @param visitLogsList
     * @Author: WanMing
     * @Date: 2019/6/28 11:56
     */
    @Override
    public void batchAddVisitLogsInfo(List<AddVisitLogsDto> visitLogsList) {
        visitLogsMongoMapper.batchInsertVisitLogsInfo(BeanMapUtils.mapList(visitLogsList, VisitLogsMongo.class));
    }
}
