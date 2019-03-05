package com.bdxh.system.service;

import com.bdxh.common.web.support.IService;
import com.bdxh.system.entity.RolePermission;

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

    // 删除角色与权限关系
    Boolean delRolePermission(Long roleId);
}
