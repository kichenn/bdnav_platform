package com.bdxh.account.feign;

import com.bdxh.account.dto.AddUserDevice;
import com.bdxh.account.dto.ModifyUserDevice;

import com.bdxh.account.entity.UserDevice;
import com.bdxh.account.fallback.UserDeviceControllerClientFallback;
import com.bdxh.common.utils.wrapper.Wrapper;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 增加用户设备信息
     */
    @RequestMapping(value = "/userDevice/addUserDeviceInfo", method = RequestMethod.POST)
    @ResponseBody
    Wrapper addUserDeviceInfo(@RequestBody AddUserDevice addUserDevice);

    /**
     * 修改用户设备信息
     */
    @RequestMapping(value = "/userDevice/modifyUserDeviceInfo", method = RequestMethod.POST)
    @ResponseBody
    Wrapper modifyUserDeviceInfo(@RequestBody ModifyUserDevice modifyUserDevice);


    /**
     * 删除用户设备信息
     */
    @RequestMapping(value = "/userDevice/delUserDeviceById", method = RequestMethod.GET)
    @ResponseBody
    Wrapper delUserDeviceById(@RequestParam("id")Long id);


    /**
     * 根据条件查询用户设备信息
     */
    @RequestMapping(value = "/userDevice/findUserDeviceByCodeOrCard", method = RequestMethod.GET)
    @ResponseBody
    Wrapper<UserDevice> findUserDeviceByCodeOrCard(@RequestParam("schoolCode")String schoolCode,@RequestParam("cardNumber")String cardNumber);


}
