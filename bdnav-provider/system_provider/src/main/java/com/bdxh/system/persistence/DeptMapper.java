package com.bdxh.system.persistence;

import com.bdxh.system.entity.Dept;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;
@Repository
public interface DeptMapper extends Mapper<Dept> {
}