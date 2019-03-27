package com.bdxh.school.persistence;

import com.bdxh.school.entity.SchoolRolePermission;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface SchoolRolePermissionMapper extends Mapper<SchoolRolePermission> {

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
    List<SchoolRolePermission> findPermissionId(@Param("roleId") Long roleId);

    /**
     * 角色id查询当前用户
     *
     * @param roleId
     * @return
     */
    List<Long> findPermissionIdByRoleId(@Param("roleId") Long roleId);

    /**
     * 根据角色id查询角色权限
     * @param roleId
     * @return
     */
    List<SchoolRolePermission> findPermissionList(@Param("roleId") Long roleId);


    /**
     * 批量删除角色，根据角色id
     *
     * @param roleIds
     * @return
     */
    Integer delBatchRoleByRoleIds(@Param("roleIds") List<Long> roleIds);
}