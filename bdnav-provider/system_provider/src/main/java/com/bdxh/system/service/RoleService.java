package com.bdxh.system.service;

import com.bdxh.common.web.support.IService;
import com.bdxh.system.entity.Role;

import java.util.List;

/**
 * @description: 角色管理service
 * @author: xuyuan
 * @create: 2019-02-22 17:05
 **/
public interface RoleService extends IService<Role> {

    void delRole(Long roleId);

    void delBatchRole(List<Long> roleIds);

}