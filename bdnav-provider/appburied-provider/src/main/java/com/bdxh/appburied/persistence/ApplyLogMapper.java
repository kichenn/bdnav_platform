package com.bdxh.appburied.persistence;

import com.bdxh.appburied.entity.ApplyLog;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface ApplyLogMapper extends Mapper<ApplyLog> {
}