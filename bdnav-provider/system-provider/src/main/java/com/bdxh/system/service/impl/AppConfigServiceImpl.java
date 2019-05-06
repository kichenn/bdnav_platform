package com.bdxh.system.service.impl;

import com.bdxh.common.support.BaseService;
import com.bdxh.system.entity.AppConfig;
import com.bdxh.system.persistence.AppConfigMapper;
import com.bdxh.system.service.AppConfigService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @description: 业务层实现
 * @author: xuyuan
 * @create: 2019-03-21 16:56
 **/
@Service
@Slf4j
public class AppConfigServiceImpl extends BaseService<AppConfig> implements AppConfigService {

    @Autowired
    private AppConfigMapper appConfigMapper;

    /**
     * 查询总条数
     * @return
     */
    @Override
    public Integer getAppConfigAllCount() {
        Integer count = appConfigMapper.getAppConfigAllCount();
        return count;
    }

    /**
     * 根据id进行批量删除
     * @param ids
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean batchDelAppConfigInIds(List<Long> ids) {
        Integer count = appConfigMapper.delAppConfigInIds(ids);
        return count>0;
    }

    @Override
    public AppConfig getByAppConfigName(String appName) {
        AppConfig appConfig = appConfigMapper.getByAppConfigName(appName);
        return appConfig;
    }

    @Override
    public List<AppConfig> getAppConfigList(Map<String, Object> param) {
        List<AppConfig> appConfigs = appConfigMapper.getByCondition(param);
        return appConfigs;
    }

    @Override
    public PageInfo<AppConfig> getAppConfigListPage(Map<String, Object> param, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<AppConfig> appConfigs = appConfigMapper.getByCondition(param);
        PageInfo<AppConfig> pageInfo = new PageInfo<>(appConfigs);
        return pageInfo;
    }

}
