package com.bdxh.system.persistence;

import com.bdxh.system.entity.Role;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;
@Repository
public interface RoleMapper extends Mapper<Role> {
}