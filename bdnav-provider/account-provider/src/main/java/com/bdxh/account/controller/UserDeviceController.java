package com.bdxh.account.controller;

import com.bdxh.account.entity.Account;
import com.bdxh.account.entity.UserDevice;
import com.bdxh.account.service.UserDeviceService;
import com.bdxh.common.utils.wrapper.WrapMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
* @Description: 控制器
* @Author Kang
* @Date 2019-05-24 14:35:27
*/
@RestController
@RequestMapping("/userDevice")
@Slf4j
@Validated
@Api(value = "UserDevice控制器", tags = "UserDevice")
public class UserDeviceController {

	@Autowired
	private UserDeviceService userDeviceService;


	@ApiOperation(value = "查询手机设备登录信息", response = Account.class)
	@RequestMapping(value = "/getUserDeviceAll", method = RequestMethod.GET)
	public Object getUserDeviceAll(@RequestParam("schoolCode")String schoolCode,@RequestParam("groupId") Long groupId) {
		List<UserDevice> accounts = userDeviceService.getUserDeviceAll(schoolCode,groupId);
		return WrapMapper.ok(accounts);
	}

}