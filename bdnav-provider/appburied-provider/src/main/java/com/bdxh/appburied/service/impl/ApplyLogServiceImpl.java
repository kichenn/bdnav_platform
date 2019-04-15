package com.bdxh.appburied.service.impl;

import com.bdxh.appburied.dto.ApplyLogQueryDto;
import com.bdxh.appburied.service.ApplyLogService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bdxh.common.support.BaseService;
import lombok.extern.slf4j.Slf4j;
import com.bdxh.appburied.entity.ApplyLog;
import com.bdxh.appburied.persistence.ApplyLogMapper;

import java.util.List;

/**
 * @Description: 业务层实现
 * @Author Kang
 * @Date 2019-04-11 16:39:55
 */
@Service
@Slf4j
public class ApplyLogServiceImpl extends BaseService<ApplyLog> implements ApplyLogService {

    @Autowired
    private ApplyLogMapper applyLogMapper;

    /*
     *查询总条数
     */
    @Override
    public Integer getApplyLogAllCount() {
        return applyLogMapper.getApplyLogAllCount();
    }

    /**
     * 分页条件查询 上报App状态日志
     */
    @Override
    public PageInfo<ApplyLog> findApplyLogInConationPaging(ApplyLogQueryDto applyLogQueryDto) {
        ApplyLog applyLog = new ApplyLog();
        BeanUtils.copyProperties(applyLogQueryDto, applyLog);
        //设置状态值
        if (applyLogQueryDto.getInstallAppsPlatformEnum() != null) {
            applyLog.setPlatform(applyLogQueryDto.getInstallAppsPlatformEnum().getKey());
        }
        if (applyLogQueryDto.getApplyLogModelEnum() != null) {
            applyLog.setModel(applyLogQueryDto.getApplyLogModelEnum().getKey());
        }
        if (applyLogQueryDto.getApplyLogOperatorStatusEnum() != null) {
            applyLog.setOperatorStatus(applyLogQueryDto.getApplyLogOperatorStatusEnum().getKey());
        }
        Page page = PageHelper.startPage(applyLogQueryDto.getPageNum(), applyLogQueryDto.getPageSize());
        List<ApplyLog> appStatuses = applyLogMapper.findApplyLogInConationPaging(applyLog);
        PageInfo pageInfo = new PageInfo<>(appStatuses);
        pageInfo.setTotal(page.getTotal());
        return pageInfo;
    }

}