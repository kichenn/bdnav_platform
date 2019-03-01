package com.bdxh.system.service.impl;

import com.bdxh.common.web.support.BaseService;
import com.bdxh.system.entity.Role;
import com.bdxh.system.persistence.RoleMapper;
import com.bdxh.system.persistence.RolePermissionMapper;
import com.bdxh.system.persistence.UserRoleMapper;
import com.bdxh.system.service.RoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @description: 角色管理service实现
 * @author: xuyuan
 * @create: 2019-02-22 17:05
 **/
@Service
@Slf4j
public class RoleServiceImpl extends BaseService<Role> implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delRole(Long roleId) {
        roleMapper.deleteByPrimaryKey(roleId);
        userRoleMapper.deleteByRoleId(roleId);
        rolePermissionMapper.deleteByRoleId(roleId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delBatchRole(List<Long> roleIds) {
        if (roleIds!=null&&!roleIds.isEmpty()){
            for (int i=0;i<roleIds.size();i++){
                Long roleId = roleIds.get(i);
                roleMapper.deleteByPrimaryKey(roleId);
                userRoleMapper.deleteByRoleId(roleId);
                rolePermissionMapper.deleteByRoleId(roleId);
            }
        }
    }


    @Override
    public List<Role> findList(Map<String, Object> param) {
        return roleMapper.getByCondition(param);
    }

    @Override
    public PageInfo<Role> findListPage(Map<String, Object> param, int pageNum, int pageSize) {
            PageHelper.startPage(pageNum,pageSize);
            List<Role> roleLogs = roleMapper.getByCondition(param);
            PageInfo<Role> pageInfo=new PageInfo<>(roleLogs);
            return pageInfo;
    }

    @Override
    public List<String> getRoleListByUserId(Long userId) {
        List<String> roles = roleMapper.getRoleListByUserId(userId);
        return roles;
    }

}
