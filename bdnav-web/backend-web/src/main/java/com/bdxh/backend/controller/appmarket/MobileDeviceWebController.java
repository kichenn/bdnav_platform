package com.bdxh.backend.controller.appmarket;


import com.bdxh.appmarket.feign.MobileDeviceControllerClient;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/MobileDeviceWeb")
@Validated
@Slf4j
@Api(value = "手机设备管理", tags = "手机设备管理")
public class MobileDeviceWebController {

    @Autowired
    private MobileDeviceControllerClient mobileDeviceControllerClient;

    @ApiOperation("查询手机设备列表")
    @RequestMapping(value = "/findMobileDevices",method = RequestMethod.GET)
    public Object findMobileDevices(){
        try {
            Wrapper wrapper = mobileDeviceControllerClient.findMobileDevices();
            return wrapper;
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }
}
