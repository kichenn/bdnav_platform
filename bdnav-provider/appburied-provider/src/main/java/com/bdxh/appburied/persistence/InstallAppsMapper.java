package com.bdxh.appburied.persistence;

import com.bdxh.appburied.entity.InstallApps;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface InstallAppsMapper extends Mapper<InstallApps> {
}