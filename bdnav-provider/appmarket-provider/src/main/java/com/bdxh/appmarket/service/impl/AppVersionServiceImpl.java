package com.bdxh.appmarket.service.impl;

import com.bdxh.appmarket.entity.AppVersion;
import com.bdxh.appmarket.persistence.AppVersionMapper;
import com.bdxh.appmarket.service.AppVersionService;
import com.bdxh.common.support.BaseService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @description: 应用版本service实现
 * @author: xuyuan
 * @create: 2019-03-04 14:57
 **/
@Service
@Slf4j
public class AppVersionServiceImpl extends BaseService<AppVersion> implements AppVersionService {

    @Autowired
    private AppVersionMapper appVersionMapper;

    @Override
    public Integer isAppVersionExist(Long appId, String appVersion) {
        Integer isAppVersionExist = appVersionMapper.isAppVersionExist(appId, appVersion);
        return isAppVersionExist;
    }

    @Override
    public List<AppVersion> queryAppVersionList(Map<String, Object> param) {
        List<AppVersion> apps = appVersionMapper.getByCondition(param);
        return apps;
    }

    @Override
    public PageInfo<AppVersion> queryAppVersionListPage(Map<String, Object> param, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<AppVersion> apps = appVersionMapper.getByCondition(param);
        PageInfo<AppVersion> pageInfo = new PageInfo<>(apps);
        return pageInfo;
    }

}
