package com.bdxh.system.fallback;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.system.entity.User;
import com.bdxh.system.feign.UserControllerClient;
import org.springframework.stereotype.Component;

/**
 * @description: 系统用户feign降级服务
 * @author: xuyuan
 * @create: 2019-02-28 12:29
 **/
@Component
public class UserControllerClientFallback implements UserControllerClient {

    @Override
    public Wrapper<User> queryUserByUserName(String userName) {
        return WrapMapper.error();
    }

}
