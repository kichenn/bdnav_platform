package com.bdxh.system.persistence;

import com.bdxh.system.entity.RolePermission;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;
@Repository
public interface RolePermissionMapper extends Mapper<RolePermission> {
}