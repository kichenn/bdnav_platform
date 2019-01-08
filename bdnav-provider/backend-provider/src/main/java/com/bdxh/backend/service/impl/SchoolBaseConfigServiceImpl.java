package com.bdxh.backend.service.impl;

import com.bdxh.backend.entity.SchoolBaseConfig;

import com.bdxh.backend.persistence.SchoolBaseConfigMapper;
import com.bdxh.backend.service.SchoolBaseConfigService;
import com.bdxh.common.web.support.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SchoolBaseConfigServiceImpl extends BaseService<SchoolBaseConfig> implements SchoolBaseConfigService {



     @Autowired
     private SchoolBaseConfigMapper schoolBaseConfigMapper;


    @Override
    public SchoolBaseConfig getBaseConfig(String schoolCode) {
        return schoolBaseConfigMapper.getBaseConfig(schoolCode);

    }
}
