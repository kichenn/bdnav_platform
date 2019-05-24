package com.bdxh.account.feign;

import com.bdxh.account.entity.UserDevice;
import com.bdxh.account.fallback.UserDeviceControllerClientFallback;
import com.bdxh.common.utils.wrapper.Wrapper;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Service
@FeignClient(value = "account-provider-cluster", fallback = UserDeviceControllerClientFallback.class)
public interface UserDeviceControllerClient {


    /**
     * 根据schoolcode+groupId列表
     * @param
     * @return
     */
    @RequestMapping(value = "/userDevice/getUserDeviceAll", method = RequestMethod.GET)
    @ResponseBody
    Wrapper<List<UserDevice>> getUserDeviceAll(@RequestParam("schoolCode")String schoolCode, @RequestParam("groupId") Long groupId);
}
