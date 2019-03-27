package com.bdxh.system.service;

import com.bdxh.common.support.IService;
import com.bdxh.system.dto.BaPermissionsDto;
import com.bdxh.system.dto.RolePermissionDto;
import com.bdxh.system.entity.RolePermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @Description: 角色权限关系
 * @Author: Kang
 * @Date: 2019/3/1 9:55
 */
public interface RolePermissionService extends IService<RolePermission> {

    // 增加角色与权限关系
    Boolean addRolePermission(RolePermission rolePermission);

    //角色id查询所有权限id
    List<Long> findPermissionIdByRoleId(Long roleId);

    //角色id查询当前用户权限
    List<RolePermission> findPermissionId(Long roleId);

    // 删除角色与权限关系
    Boolean delRolePermission(Long roleId);

    //根据角色id和启动条件查询
    List<RolePermission> findPermissionList(Long roleId,Integer RpSwitch);



}
