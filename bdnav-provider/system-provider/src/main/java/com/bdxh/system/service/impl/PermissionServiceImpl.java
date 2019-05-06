package com.bdxh.system.service.impl;

import com.bdxh.common.support.BaseService;
import com.bdxh.system.dto.AddPermissionDto;
import com.bdxh.system.dto.ModifyPermissionDto;
import com.bdxh.system.dto.RolePermissionDto;
import com.bdxh.system.dto.UserPermissionDto;
import com.bdxh.system.entity.Permission;
import com.bdxh.system.persistence.PermissionMapper;
import com.bdxh.system.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
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

    //用户id查询权限菜单
    @Override
    public List<String> permissionMenusByUserId(Long userId) {
        List<String> permissionMenus = new ArrayList<>();
        List<Permission> permissions = permissionMapper.findPermissionByUserId(userId);
        if (CollectionUtils.isNotEmpty(permissions)) {
            permissions.stream().forEach(e -> {
                permissionMenus.add(e.getName());
            });
        }
        return permissionMenus;
    }

    //增加权限列表信息
    @Override
    public Boolean addPermission(AddPermissionDto addPermissionDto) {
        Permission permission = new Permission();
        BeanUtils.copyProperties(addPermissionDto, permission);
        if (new Long("-1").equals(permission.getParentId())) {
            permission.setParentIds("");
            permission.setName(addPermissionDto.getPath());
            permission.setPath("/" + addPermissionDto.getPath());

        } else {
            Permission sysPermissions = findPermissionById(permission.getParentId());
            permission.setName(addPermissionDto.getPath());
            permission.setParentIds(sysPermissions.getParentIds() + "," + sysPermissions.getId());
        }
        return permissionMapper.insertSelective(permission) > 0;
    }

    //修改权限列表信息
    @Override
    public Boolean modifyPermission(ModifyPermissionDto mdifyPermissionDto) {
        Permission permission = new Permission();
        BeanUtils.copyProperties(mdifyPermissionDto, permission);
        if (new Long("-1").equals(permission.getParentId())) {
            permission.setParentIds("");
            permission.setPath("/" + mdifyPermissionDto.getPath());
        } else {
            Permission sysPermissions = findPermissionById(permission.getParentId());
            permission.setName(mdifyPermissionDto.getPath());
            permission.setParentIds(sysPermissions.getParentIds() + "," + sysPermissions.getId());
        }
        return permissionMapper.updateByPrimaryKeySelective(permission) > 0;
    }


    //Id删除权限列表信息
    @Override
    @Transactional(rollbackFor = Exception.class)
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
        return permissionMapper.theTreeMenu(roleId, selected);
    }

    //根据id查询权限信息
    @Override
    public Permission findPermissionById(Long id) {
        return permissionMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Permission> findByTitle(String title) {
        return permissionMapper.findByTitle(title);
    }

    @Override
    public Permission findPermissionByParentId(Long parentId) {
        return permissionMapper.findPermissionByParentId(parentId);
    }

    @Override
    public List<UserPermissionDto> findUserRights(Long userId, Byte type) {
        return permissionMapper.findUserRights(userId, type);
    }


}
