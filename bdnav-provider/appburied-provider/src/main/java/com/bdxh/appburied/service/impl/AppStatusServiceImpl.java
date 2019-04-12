package com.bdxh.appburied.service.impl;

import com.bdxh.appburied.dto.AppStatusQueryDto;
import com.bdxh.appburied.service.AppStatusService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bdxh.common.support.BaseService;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import com.bdxh.appburied.entity.AppStatus;
import com.bdxh.appburied.persistence.AppStatusMapper;

import java.util.List;

/**
 * @Description: 业务层实现
 * @Author Kang
 * @Date 2019-04-11 16:39:55
 */
@Service
@Slf4j
public class AppStatusServiceImpl extends BaseService<AppStatus> implements AppStatusService {

    @Autowired
    private AppStatusMapper appStatusMapper;

    /*
     *查询总条数
     */
    @Override
    public Integer getAppStatusAllCount() {
        return appStatusMapper.getAppStatusAllCount();
    }


    /**
     * 条件分页查询app状态
     */
    @Override
    public PageInfo<AppStatus> findAppStatusInConationPaging(AppStatusQueryDto appStatusQueryDto) {

        AppStatus appStatus = new AppStatus();
        BeanUtils.copyProperties(appStatusQueryDto, appStatus);
        //设置状态值
        if (appStatusQueryDto.getAppStatusEnum() != null) {
            appStatus.setAppStatus(appStatusQueryDto.getAppStatusEnum().getKey());
        }
        if (appStatusQueryDto.getInstallAppsPlatformEnum() != null) {
            appStatus.setPlatform(appStatusQueryDto.getInstallAppsPlatformEnum().getKey());
        }

        Page page = PageHelper.startPage(appStatusQueryDto.getPageNum(), appStatusQueryDto.getPageSize());

        List<AppStatus> appStatuses = appStatusMapper.findAppStatusInConationPaging(appStatus);

        PageInfo pageInfo = new PageInfo<>(appStatuses);
        pageInfo.setTotal(page.getTotal());

        return pageInfo;
    }
}
