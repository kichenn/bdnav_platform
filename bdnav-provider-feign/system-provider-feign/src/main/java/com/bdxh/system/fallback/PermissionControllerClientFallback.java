package com.bdxh.system.fallback;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.system.dto.*;
import com.bdxh.system.entity.Permission;
import com.bdxh.system.feign.PermissionControllerClient;
import com.bdxh.system.vo.PermissionTreeVo;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @description: 系统权限feign降级服务
 * @author: xuyuan
 * @create: 2019-02-28 12:29
 **/
@Component
public class PermissionControllerClientFallback implements PermissionControllerClient {

    @Override
    public Wrapper<List<String>> permissionMenus(Long roleId) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<List<String>> permissionMenusByUserId(Long userId) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<List<PermissionTreeVo>> findPermissionByRoleId(Long roleId, Byte type) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper addPermission(AddPermissionDto addPermissionDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper modifyPermission(ModifyPermissionDto modifyPermissionDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<List<PermissionTreeVo>> theTreeMenu(Long roleId) {
        return WrapMapper.error();
    }


    @Override
    public Wrapper addOrUpdatePermission(BaPermissionsDto baPermissionsDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper thePermissionMenu() {
        return WrapMapper.error();
    }

    @Override
    public Wrapper delPermissionById(Long id) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<Permission> findPermissionById(Long id) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<Permission> findPermissionByParentId(Long parentId) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<List<PermissionTreeVo>> userPermissionMenu(Long userId) {
        return WrapMapper.error();
    }


}
