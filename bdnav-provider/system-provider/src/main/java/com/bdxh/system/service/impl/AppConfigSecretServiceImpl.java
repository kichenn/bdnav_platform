package com.bdxh.system.service.impl;

import com.bdxh.common.support.BaseService;
import com.bdxh.system.entity.AppConfigSecret;
import com.bdxh.system.persistence.AppConfigSecretMapper;
import com.bdxh.system.service.AppConfigSecretService;
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
 * @create: 2019-03-21 16:55
 **/
@Service
@Slf4j
public class AppConfigSecretServiceImpl extends BaseService<AppConfigSecret> implements AppConfigSecretService {

    @Autowired
    private AppConfigSecretMapper appConfigSecretMapper;

    @Override
    public Integer getAppConfigSecretAllCount() {
        Integer count = appConfigSecretMapper.getAppConfigSecretAllCount();
        return count;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean batchDelAppConfigSecretInIds(List<Long> ids) {
        Integer count = appConfigSecretMapper.delAppConfigSecretInIds(ids);
        return count>0;
    }

    @Override
    public Integer isAppConfigSecretExist(Map<String, Object> param) {
        Integer exist = appConfigSecretMapper.isAppConfigSecretExist(param);
        return exist;
    }

    @Override
    public List<AppConfigSecret> getAppConfigSecretList(Map<String, Object> param) {
        List<AppConfigSecret> appConfigSecrets = appConfigSecretMapper.getByCondition(param);
        return appConfigSecrets;
    }

    @Override
    public PageInfo<AppConfigSecret> getAppConfigSecretListPage(Map<String, Object> param, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<AppConfigSecret> appConfigSecrets = appConfigSecretMapper.getByCondition(param);
        PageInfo<AppConfigSecret> pageInfo = new PageInfo<>(appConfigSecrets);
        return pageInfo;
    }

}
