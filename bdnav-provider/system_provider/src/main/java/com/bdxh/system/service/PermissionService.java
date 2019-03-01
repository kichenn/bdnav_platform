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

<<<<<<< HEAD
    //角色id查询父节点
    Permission findPermissionByRoleId(Long roleId, Byte type, Integer level);

    //父id查询节点信息
    List<Permission> findPermissionByParentId(Long id);

//    List<Permission>
=======
    /**
     * 根据用户id查询权限列表
     * @param userId
     * @return
     */
    List<String> getPermissionListByUserId(Long userId);

>>>>>>> b879fcd9e0f1969a63c90cc2d853ab080a5d1477
}
