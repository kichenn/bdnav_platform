package com.bdxh.appmarket.fallback;

import com.bdxh.appmarket.entity.MobileDevice;
import com.bdxh.appmarket.feign.MobileDeviceControllerClient;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class MobileDeviceControllerClientFallback implements MobileDeviceControllerClient {
    @Override
    public Wrapper<List<MobileDevice>> findMobileDevices() {
        return WrapMapper.error();
    }
}
