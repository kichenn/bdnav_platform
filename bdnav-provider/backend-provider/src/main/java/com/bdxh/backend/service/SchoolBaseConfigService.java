package com.bdxh.backend.service;

import com.bdxh.backend.entity.SchoolBaseConfig;
import com.bdxh.common.web.support.IService;

public interface SchoolBaseConfigService extends IService<SchoolBaseConfig>{
    //根据schoolcode查询学校基本信息
    public SchoolBaseConfig getBaseConfig(String schoolCode) ;
}
