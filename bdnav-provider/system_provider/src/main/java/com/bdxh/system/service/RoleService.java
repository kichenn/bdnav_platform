package com.bdxh.system.service;

import com.bdxh.common.web.support.IService;
import com.bdxh.system.entity.Role;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * @description: 角色管理service
 * @author: xuyuan
 * @create: 2019-02-22 17:05
 **/
public interface RoleService extends IService<Role> {

    void delRole(Long roleId);

    void delBatchRole(List<Long> roleIds);

    List<Role> findList(Map<String,Object> param);

    PageInfo<Role> findRoleList(Map<String,Object> param, int pageNum, int pageSize);


}