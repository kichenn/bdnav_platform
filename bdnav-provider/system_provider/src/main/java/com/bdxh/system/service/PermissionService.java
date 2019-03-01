package com.bdxh.system.service;

import com.bdxh.common.web.support.IService;
import com.bdxh.system.entity.Permission;

import java.util.List;

/**
 * @description: 权限管理service
 * @author: xuyuan
 * @create: 2019-02-22 17:08
 **/
public interface PermissionService extends IService<Permission> {

    /**
     * 根据用户id查询权限列表
     * @param userId
     * @return
     */
    List<String> getPermissionListByUserId(Long userId);

}
