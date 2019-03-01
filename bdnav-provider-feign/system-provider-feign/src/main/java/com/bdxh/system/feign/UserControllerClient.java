package com.bdxh.system.feign;

import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.system.entity.User;
import com.bdxh.system.fallback.UserControllerClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @description: 系统用户feign客户端
 * @author: xuyuan
 * @create: 2019-02-28 12:29
 **/
@Service
@FeignClient(value = "system-provider-cluster",fallback= UserControllerClientFallback.class)
public interface UserControllerClient {

    /**
     * 根据用户名查询用户对象
     * @param userName
     * @return
     */
    @RequestMapping(value = "/queryUserByUserName",method = RequestMethod.GET)
    @ResponseBody
    Wrapper<User> queryUserByUserName(String userName);

}
