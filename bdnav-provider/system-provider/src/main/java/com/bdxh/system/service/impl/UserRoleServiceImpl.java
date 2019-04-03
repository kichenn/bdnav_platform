package com.bdxh.system.service.impl;

import com.bdxh.common.support.BaseService;
import com.bdxh.common.support.IService;
import com.bdxh.system.entity.Role;
import com.bdxh.system.entity.UserRole;
import com.bdxh.system.persistence.UserRoleMapper;
import com.bdxh.system.service.UserRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description: 用户角色管理service实现
 * @author: xuyuan
 * @create: 2019-02-22 17:04
 **/
@Service
@Slf4j
public class UserRoleServiceImpl extends BaseService<UserRole> implements UserRoleService {

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public List<Role> findUserRole(Long RoleId) {
        return userRoleMapper.findUserRole(RoleId);
    }

    @Override
    public List<UserRole> findUserRoleByUserId(Long userId) {
        return userRoleMapper.findUserRoleByUserId(userId);
    }

    @Override
    public List<UserRole> findUserRoleOnly(Long userId) {
        return userRoleMapper.findUserRoleOnly(userId);
    }
}
