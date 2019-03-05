package com.bdxh.system.service.impl;

import com.bdxh.common.web.support.BaseService;
import com.bdxh.common.web.support.IService;
import com.bdxh.system.entity.RolePermission;
import com.bdxh.system.persistence.RolePermissionMapper;
import com.bdxh.system.service.RolePermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: 角色权限关系service
 * @Author: Kang
 * @Date: 2019/3/1 9:57
 */
@Service
@Slf4j
public class RolePermissionServiceImpl extends BaseService<RolePermission> implements RolePermissionService {

    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    // 增加角色与权限关系
    @Override
    public Boolean addRolePermission(RolePermission rolePermission) {
        return rolePermissionMapper.insertSelective(rolePermission) > 0;
    }

    //角色id查询所有权限id
    @Override
    public List<Long> findPermissionIdByRoleId(Long roleId) {
        return rolePermissionMapper.findPermissionIdByRoleId(roleId);
    }

    // 删除角色与权限关系
    @Override
    public Boolean delRolePermission(Long roleId) {
        return rolePermissionMapper.deleteByRoleId(roleId) > 0;
    }

}
