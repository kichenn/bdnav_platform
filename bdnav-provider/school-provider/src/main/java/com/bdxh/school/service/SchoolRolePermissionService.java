package com.bdxh.school.service;

import com.bdxh.common.support.IService;
import com.bdxh.school.dto.AddRolePermissionBindMenuDto;
import com.bdxh.school.entity.SchoolRolePermission;

import java.util.List;


/**
* @Description:  学校角色权限关系
* @Author: Kang
* @Date: 2019/3/26 12:05
*/
public interface SchoolRolePermissionService extends IService<SchoolRolePermission> {

    // 增加角色与权限关系
    Boolean addRolePermission(SchoolRolePermission rolePermission);

    //角色id查询所有权限id
    List<Long> findPermissionIdByRoleId(Long roleId);

    //角色id查询当前用户权限
    List<SchoolRolePermission> findPermissionId(Long roleId);

    // 删除角色与权限关系
    Boolean delRolePermission(Long roleId);

    //角色与权限菜单的捆绑
    void addRolePermissionBindMenu(AddRolePermissionBindMenuDto addRolePermissionBindMenu);
}
