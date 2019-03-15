package com.bdxh.system.fallback;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.system.dto.AddPermissionDto;
import com.bdxh.system.dto.ModifyPermissionDto;
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
    public Wrapper<List<String>> permissionMenus(Long userId) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<List<PermissionTreeVo>> findPermissionByRoleId(Long roleId, Byte type) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper AddPermissionDto(AddPermissionDto dto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper modifyPermission(ModifyPermissionDto dto) {
        return WrapMapper.error();
    }

}
