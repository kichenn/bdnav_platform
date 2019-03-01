package com.bdxh.system.fallback;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.system.feign.RoleControllerClient;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @description: 系统角色feign降级服务
 * @author: xuyuan
 * @create: 2019-02-28 12:29
 **/
@Component
public class RoleControllerClientFallback implements RoleControllerClient {

    @Override
    public Wrapper<List<String>> queryRoleListByUserId(Long userId) {
        return WrapMapper.error();
    }

}
