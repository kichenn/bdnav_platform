package com.bdxh.system.persistence;

import com.bdxh.system.entity.RolePermission;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface RolePermissionMapper extends Mapper<RolePermission> {

    /**
     * 根据角色id删除关联表
     *
     * @param roleId
     */
    Integer deleteByRoleId(@Param("roleId") Long roleId);

    /**
     * 角色id查询所有权限id
     *
     * @param roleId
     * @return
     */
    List<Long> findPermissionIdByRoleId(@Param("roleId") Long roleId);

}