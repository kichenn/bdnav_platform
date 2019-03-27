package com.bdxh.system.service;


import com.bdxh.common.support.IService;
import com.bdxh.system.dto.RolePermissionDto;
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

    //查询所有菜单 /选中状态 默认给2 未选中显示全部
    List<RolePermissionDto> theTreeMenu(Long roleId, Integer selected);

  /*  //根据角色id查询权限菜单
    List<Permission> permissionByMenus(Long roleId,Integer selected);*/

    /**
     * 根据id查询权限信息
     * @param id
     * @return
     */
    Permission findPermissionById(Long id);

    List<Permission> findByTitle(String title);

}
