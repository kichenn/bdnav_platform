package com.bdxh.system.service.impl;

import com.bdxh.common.web.support.BaseService;
import com.bdxh.system.entity.Permission;
import com.bdxh.system.persistence.PermissionMapper;
import com.bdxh.system.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private PermissionMapper permissionMapper;


    //角色id查询权限菜单
    @Override
    public List<Permission> findPermissionByRoleId(Long roleId, Byte type) {
        return permissionMapper.findPermissionByRoleId(roleId, type);
    }

    //增加权限列表信息
    @Override
    public Boolean addPermission(Permission permission) {
        return permissionMapper.insertSelective(permission) > 0;
    }

    //修改权限列表信息
    @Override
    public Boolean modifyPermission(Permission permission) {
        return permissionMapper.updateByPrimaryKeySelective(permission) > 0;
    }

    //Id删除权限列表信息
    @Override
    public Boolean delPermissionById(Long id) {
        return permissionMapper.deleteByPrimaryKey(id) > 0;
    }


    //批量删除权限列表信息
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean batchDelPermission(List<Long> ids) {
        return permissionMapper.batchDelPermissionInIds(ids) > 0;
    }


}
