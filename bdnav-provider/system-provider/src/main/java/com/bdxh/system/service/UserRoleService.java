package com.bdxh.system.service;

import com.bdxh.common.support.IService;
import com.bdxh.system.entity.Role;
import com.bdxh.system.entity.UserRole;

import java.util.List;

/**
 * @description: 用户角色管理service
 * @author: xuyuan
 * @create: 2019-02-22 17:04
 **/
public interface UserRoleService extends IService<UserRole> {

      List<Role> findUserRole(Long RoleId);
}
