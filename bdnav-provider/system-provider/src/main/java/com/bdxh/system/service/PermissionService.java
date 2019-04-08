package com.bdxh.system.service;


import com.bdxh.common.support.IService;
import com.bdxh.system.dto.AddPermissionDto;
import com.bdxh.system.dto.ModifyPermissionDto;
import com.bdxh.system.dto.RolePermissionDto;
import com.bdxh.system.dto.UserPermissionDto;
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

    //用户id查询权限菜单
    List<String> permissionMenusByUserId(Long userId);

    //增加权限列表信息
    Boolean addPermission(AddPermissionDto addPermissionDto);

    //修改权限列表信息
    Boolean modifyPermission(ModifyPermissionDto mdifyPermissionDto);

    //Id删除权限列表信息
    Boolean delPermissionById(Long id);

    //批量删除权限列表信息
    Boolean batchDelPermission(List<Long> ids);

    //查询所有菜单 /选中状态 默认给2 未选中显示全部
    List<RolePermissionDto> theTreeMenu(Long roleId, Integer selected);

    //根据id查询权限信息
    Permission findPermissionById(Long id);

    //根据名称查询所有列表
    List<Permission> findByTitle(String title);

    //父级id查询部门信息
    Permission findPermissionByParentId(Long parentId);

    //根据用户id查询所有权限
    List<UserPermissionDto> findUserRights(Long userId, Byte type);


}
