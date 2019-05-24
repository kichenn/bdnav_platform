package com.bdxh.account.fallback;

import com.bdxh.account.feign.UserDeviceControllerClient;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import org.springframework.stereotype.Component;

@Component
public class UserDeviceControllerClientFallback implements UserDeviceControllerClient {


    @Override
    public Wrapper getUserDeviceAll(String schoolCode, Long groupId) {
        return WrapMapper.error();
    }
}
