package com.bdxh.appburied.service.impl;

import com.bdxh.appburied.dto.InstallAppsQueryDto;
import com.bdxh.appburied.service.InstallAppsService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bdxh.common.support.BaseService;
import lombok.extern.slf4j.Slf4j;
import com.bdxh.appburied.entity.InstallApps;
import com.bdxh.appburied.persistence.InstallAppsMapper;

import java.util.List;

/**
 * @Description: 业务层实现
 * @Author Kang
 * @Date 2019-04-11 16:39:55
 */
@Service
@Slf4j
public class InstallAppsServiceImpl extends BaseService<InstallApps> implements InstallAppsService {

    @Autowired
    private InstallAppsMapper installAppsMapper;

    /*
     *查询总条数
     */
    @Override
    public Integer getInstallAppsAllCount() {
        return installAppsMapper.getInstallAppsAllCount();
    }


    /**
     * 条件分页查询上报app应用
     */
    @Override
    public PageInfo<InstallApps> findInstallAppsInConationPaging(InstallAppsQueryDto installAppsQueryDto) {
        InstallApps installApps = new InstallApps();
        BeanUtils.copyProperties(installAppsQueryDto, installApps);
        if (installAppsQueryDto.getInstallAppsPlatformEnum() != null) {
            installApps.setPlatform(installAppsQueryDto.getInstallAppsPlatformEnum().getKey());
        }
        PageHelper.startPage(installAppsQueryDto.getPageNum(), installAppsQueryDto.getPageSize());
        List<InstallApps> appStatuses = installAppsMapper.findInstallAppsInContionPaging(installApps);
        PageInfo<InstallApps> pageInfoFamily = new PageInfo<>(appStatuses);
        return pageInfoFamily;
    }

    @Override
    public List<InstallApps> findInstallAppsInConation(String schoolCode, String cardNumber) {
        InstallApps installApps=new InstallApps();
        installApps.setSchoolCode(schoolCode);
        installApps.setCardNumber(cardNumber);
        return installAppsMapper.findInstallAppsInContionPaging(installApps);
    }
}
