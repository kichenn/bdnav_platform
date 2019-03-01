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

    //角色id查询权限菜单
    List<Permission> findPermissionByRoleId(Long roleId,Byte type);

    //父id查询节点信息
    List<Permission> findPermissionByParentId(Long id);

    /**
     * 根据用户id查询权限列表
     * @param userId
     * @return
     */
    List<String> getPermissionListByUserId(Long userId);

}
