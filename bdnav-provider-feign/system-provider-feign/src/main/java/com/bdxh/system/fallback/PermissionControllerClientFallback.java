package com.bdxh.system.fallback;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.system.feign.PermissionControllerClient;
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

}
