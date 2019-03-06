package com.bdxh.system.service;

import com.bdxh.common.web.support.IService;
import com.bdxh.system.entity.Permission;

import java.util.List;

/**
 * @Description: 权限操作
 * @Author: Kang
 * @Date: 2019/3/1 9:35
 */
public interface PermissionService extends IService<Permission> {

    //角色id查询权限菜单or按钮
    List<Permission> findPermissionByRoleId(Long roleId, Byte type);

    //角色id查询权限菜单
    List<String> permissionMenus(Long roleId);

    //增加权限列表信息
    Boolean addPermission(Permission permission);

    //修改权限列表信息
    Boolean modifyPermission(Permission permission);

    //Id删除权限列表信息
    Boolean delPermissionById(Long id);

    //批量删除权限列表信息
    Boolean batchDelPermission(List<Long> ids);
}
