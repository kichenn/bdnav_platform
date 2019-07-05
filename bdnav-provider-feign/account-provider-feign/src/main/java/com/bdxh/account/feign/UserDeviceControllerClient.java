package com.bdxh.account.feign;

import com.bdxh.account.dto.AddUserDevice;
import com.bdxh.account.dto.ModifyUserDevice;

import com.bdxh.account.dto.QueryUserDeviceDto;
import com.bdxh.account.entity.UserDevice;
import com.bdxh.account.fallback.UserDeviceControllerClientFallback;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
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
    Wrapper<List<String>> getUserDeviceAll(@RequestParam("schoolCode")String schoolCode, @RequestParam("ids") String ids);

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

    /**
     * 根据schoolcode查询所属学校写下设备列表
     * @param
     * @return
     */
    @RequestMapping(value = "/userDevice/findUserDeviceList", method = RequestMethod.GET)
    @ResponseBody
    Wrapper<List<UserDevice>> findUserDeviceList(@RequestParam("schoolCode")String schoolCode);

    /**
     * 根据条件查找用户设备信息
     * @Author: WanMing
     * @Date: 2019/7/5 10:14
     */
    @RequestMapping(value = "/userDevice/findUserDeviceByCondition",method = RequestMethod.POST)
    @ResponseBody
    Wrapper<PageInfo<UserDevice>> findUserDeviceByCondition(@RequestBody QueryUserDeviceDto queryUserDeviceDto);

}
