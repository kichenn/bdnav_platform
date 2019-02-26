package com.bdxh.system.persistence;

import com.bdxh.system.entity.Permission;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;
@Repository
public interface PermissionMapper extends Mapper<Permission> {
}