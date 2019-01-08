package com.bdxh.backend.persistence;

import com.bdxh.backend.entity.SchoolBaseConfig;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;
@Repository
public interface SchoolBaseConfigMapper extends Mapper<SchoolBaseConfig> {

    /**
     * 根据schoolCode获取学校基本信息
     * @param schoolCode
     * @return
     */
    public SchoolBaseConfig getBaseConfig(String schoolCode) ;
}