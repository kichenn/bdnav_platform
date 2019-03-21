package com.bdxh.system.service.impl;

import com.bdxh.common.support.BaseService;
import com.bdxh.system.dto.RolePermissionDto;
import com.bdxh.system.entity.Permission;
import com.bdxh.system.persistence.PermissionMapper;
import com.bdxh.system.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    //权限类型（type：1菜单，2按钮）
    private static final Byte permissionType = new Byte("1");

    //角色id查询权限菜单
    @Override
    public List<Permission> findPermissionByRoleId(Long roleId, Byte type) {
        return permissionMapper.findPermissionByRoleId(roleId, type);
    }

    //角色id查询权限菜单
    @Override
    public List<String> permissionMenus(Long roleId) {
        List<String> permissionMenus = new ArrayList<>();
        List<Permission> permissions = permissionMapper.findPermissionByRoleId(roleId, permissionType);
        if (CollectionUtils.isNotEmpty(permissions)) {
            permissions.stream().forEach(e -> {
                permissionMenus.add(e.getName());
            });
        }
        return permissionMenus;
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

    @Override
    public List<RolePermissionDto> theTreeMenu(Long roleId, Integer selected) {
        return permissionMapper.theTreeMenu(roleId,selected);
    }


    @Override
    public List<Permission> permissionByMenus(Long roleId, Integer selected) {
        return permissionMapper.findPermission(roleId,selected);
    }

}
