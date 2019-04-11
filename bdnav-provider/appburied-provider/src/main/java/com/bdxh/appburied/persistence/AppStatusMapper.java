package com.bdxh.appburied.persistence;

import com.bdxh.appburied.entity.AppStatus;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface AppStatusMapper extends Mapper<AppStatus> {
}