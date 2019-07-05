package com.bdxh.backend.controller.account;


import com.bdxh.account.dto.QueryUserDeviceDto;
import com.bdxh.account.entity.UserDevice;
import com.bdxh.account.feign.UserDeviceControllerClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**终端登录设备前端控制器
 * @author WanMing
 * @date 2019/7/5 10:48
 */
@RestController
@RequestMapping("/userDeviceWeb")
@Slf4j
@Validated
@Api(value = "终端登录设备控制器", tags = "终端登录设备Web控制器")
public class UserDeviceWebController {


    @Autowired
    private UserDeviceControllerClient userDeviceControllerClient;


    /**
     * 根据条件查找用户的登录设备信息
     * @Author: WanMing
     * @Date: 2019/7/5 10:14
     */
    @RequestMapping(value = "/findUserDeviceByCondition",method = RequestMethod.POST)
    @ApiOperation(value = "根据条件查找用户的登录设备信息",response = UserDevice.class)
    public Object findUserDeviceByCondition(@RequestBody QueryUserDeviceDto queryUserDeviceDto){
        return userDeviceControllerClient.findUserDeviceByCondition(queryUserDeviceDto);
    }

    /**
     * @Description: 删除用户登录设备信息
     * @Date 2019/7/5 10:20
     */
    @RequestMapping(value = "/delUserDeviceById", method = RequestMethod.GET)
    @ApiOperation(value = "删除用户登录设备信息", response = Boolean.class)
    public Object delUserDeviceById(@RequestParam("id")Long id) {
        return userDeviceControllerClient.delUserDeviceById(id);
    }

}
