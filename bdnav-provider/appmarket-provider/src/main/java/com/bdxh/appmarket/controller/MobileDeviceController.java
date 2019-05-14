package com.bdxh.appmarket.controller;

import java.util.List;


import com.bdxh.appmarket.entity.MobileDevice;
import com.bdxh.appmarket.service.MobileDeviceService;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.bdxh.common.utils.wrapper.WrapMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;




/**
* @Description: 控制器
* @Date 2019-04-22 16:28:40
*/
@Controller
@RequestMapping("/mobileDevice")
@Slf4j
@Validated
@Api(value = "手机设备控制器", tags = "手机设备控制器")
public class  MobileDeviceController {

	@Autowired
	private MobileDeviceService mobileDeviceService;


	/**
	* @Description: 查询列表信息
	* @Date 2019-04-22 16:28:40
	*/
	@RequestMapping(value = "/findMobileDevices", method = RequestMethod.GET)
	@ApiOperation(value = "查询全部设备列表")
	@ResponseBody
	public Object findMobileDevices() {
		List<MobileDevice> datas = mobileDeviceService.selectAll();
		return WrapMapper.ok(datas);
	}

	/**
	* @Description: 删除信息
	* @Date 2019-04-22 16:28:40
	*/
	@RequestMapping(value = "/delDataById", method = RequestMethod.POST)
	@ApiOperation(value = "删除信息", response = Boolean.class)
	@ResponseBody
	public Object delDataById(@RequestParam("id")Long id) {
		return WrapMapper.ok(mobileDeviceService.delMobileDeviceById(id));
	}


	/**
	* @Description: 批量删除
	* @Date 2019-04-22 16:28:40
	*/
	@RequestMapping(value = "/batchDelDataInIds", method = RequestMethod.POST)
	@ApiOperation(value = "批量删除", response = Boolean.class)
	@ResponseBody
	public Object batchDelDataInIds(@RequestParam("ids")List<Long> ids) {
		return WrapMapper.ok(mobileDeviceService.batchDelMobileDeviceInIds(ids));
	}

}