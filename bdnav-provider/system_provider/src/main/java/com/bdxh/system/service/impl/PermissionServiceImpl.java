package com.bdxh.system.service.impl;

import com.bdxh.common.web.support.BaseService;
import com.bdxh.system.entity.Permission;
import com.bdxh.system.persistence.PermissionMapper;
import com.bdxh.system.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: 权限操作service
 * @Author: Kang
 * @Date: 2019/3/1 9:35
 */
@Service
@Slf4j
public class PermissionServiceImpl extends BaseService<Permission> implements PermissionService {

    @Autowired
<<<<<<< HEAD
    private PermissionMapper permissionMapper;


    //角色id查询父节点
    @Override
    public Permission findPermissionByRoleId(Long roleId, Byte type, Integer level) {
        return null;
    }

    //父id查询节点信息
    @Override
    public List<Permission> findPermissionByParentId(Long id) {
        return null;
    }
=======
    private PermissionService permissionService;

    @Override
    public List<String> getPermissionListByUserId(Long userId) {
        List<String> permissions = permissionService.getPermissionListByUserId(userId);
        return permissions;
    }

>>>>>>> b879fcd9e0f1969a63c90cc2d853ab080a5d1477
}
