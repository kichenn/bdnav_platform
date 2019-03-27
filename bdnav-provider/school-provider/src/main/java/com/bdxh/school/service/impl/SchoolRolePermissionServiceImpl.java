package com.bdxh.school.service.impl;

import com.bdxh.common.support.BaseService;
import com.bdxh.school.entity.SchoolRolePermission;
import com.bdxh.school.persistence.SchoolRolePermissionMapper;
import com.bdxh.school.service.SchoolRolePermissionService;
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
public class SchoolRolePermissionServiceImpl extends BaseService<SchoolRolePermission> implements SchoolRolePermissionService {

    @Autowired
    private SchoolRolePermissionMapper schoolRolePermissionMapper;

    // 增加角色与权限关系
    @Override
    public Boolean addRolePermission(SchoolRolePermission rolePermission) {
        return schoolRolePermissionMapper.insertSelective(rolePermission) > 0;
    }

    //角色id查询所有权限id
    @Override
    public List<SchoolRolePermission> findPermissionId(Long roleId) {
        return schoolRolePermissionMapper.findPermissionId(roleId);
    }

    //角色id查询所有权限id
    @Override
    public List<Long> findPermissionIdByRoleId(Long roleId) {
        return schoolRolePermissionMapper.findPermissionIdByRoleId(roleId);
    }

    // 删除角色与权限关系
    @Override
    public Boolean delRolePermission(Long roleId) {
        return schoolRolePermissionMapper.deleteByRoleId(roleId) > 0;
    }

}
