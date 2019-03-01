package com.bdxh.system.feign;

import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.system.fallback.PermissionControllerClientFallback;
import com.bdxh.system.fallback.UserControllerClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @description: 系统权限feign客户端
 * @author: xuyuan
 * @create: 2019-02-28 12:29
 **/
@Service
@FeignClient(value = "system-provider-cluster",fallback= PermissionControllerClientFallback.class)
public interface PermissionControllerClient {

    /**
     * 根据用户id查询权限列表
     * @param userId
     * @return
     */
    @RequestMapping(value = "/queryRoleListByUserId",method = RequestMethod.GET)
    @ResponseBody
    Wrapper<List<String>> queryRoleListByUserId(Long userId);

}
