package com.bdxh.appmarket.feign;


import com.bdxh.appmarket.entity.AppCategory;
import com.bdxh.appmarket.entity.MobileDevice;
import com.bdxh.appmarket.fallback.MobileDeviceControllerClientFallback;
import com.bdxh.common.utils.wrapper.Wrapper;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Service
@FeignClient(value = "appmarket-provider-cluster",fallback = MobileDeviceControllerClientFallback.class)
public interface MobileDeviceControllerClient {

    @RequestMapping(value = "/mobileDevice/findMobileDevices",method = RequestMethod.GET)
    @ResponseBody
    Wrapper<List<MobileDevice>> findMobileDevices();

}
