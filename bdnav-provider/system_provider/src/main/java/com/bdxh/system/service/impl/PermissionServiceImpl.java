package com.bdxh.system.service.impl;

import com.bdxh.common.web.support.BaseService;
import com.bdxh.system.entity.Permission;
import com.bdxh.system.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description: 权限管理service实现
 * @author: xuyuan
 * @create: 2019-02-22 17:08
 **/
@Service
@Slf4j
public class PermissionServiceImpl extends BaseService<Permission> implements PermissionService {

    @Autowired
    private PermissionService permissionService;

    @Override
    public List<String> getPermissionListByUserId(Long userId) {
        List<String> permissions = permissionService.getPermissionListByUserId(userId);
        return permissions;
    }

}
