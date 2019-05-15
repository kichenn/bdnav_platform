package com.bdxh.appmarket.controller;

import com.bdxh.appmarket.dto.AddAppVersionDto;
import com.bdxh.appmarket.entity.AppVersion;
import com.bdxh.appmarket.service.AppVersionService;
import com.bdxh.common.utils.BeanMapUtils;
import com.bdxh.common.utils.wrapper.WrapMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
* @Description: 控制器
* @Author Kang
* @Date 2019-05-14 12:15:05
*/
@RestController
@RequestMapping("/appVersion")
@Slf4j
@Validated
@Api(value = "APP版本控制器控制器", tags = "APP版本控制器控制器")
public class AppVersionController {

	@Autowired
	private AppVersionService appVersionService;

	/**
	 * 添加app新的版本信息
	 * @param addAppVersionDto
	 * @return
	 */
	@RequestMapping(value = "/addAppVersion",method = RequestMethod.POST)
	@ApiOperation("添加app新的版本信息")
	public Object addAppVersion(@Valid @RequestBody AddAppVersionDto addAppVersionDto){
		AppVersion appVersion= BeanMapUtils.map(addAppVersionDto,AppVersion.class);
		appVersionService.addAppVersionInfo(appVersion);
		return WrapMapper.ok();
	}
	/**
	 * 查看APP版本历史
	 */
	@RequestMapping(value = "/findAppVersion",method = RequestMethod.GET)
	@ApiOperation("查看APP版本历史")
	public Object findAppVersion(@RequestParam("appId")Long appId){
		return appVersionService.findAppVersion(appId);
	}


	/**
	 * 最新版本APP版本
	 */
	@RequestMapping(value = "/findNewAppVersion",method = RequestMethod.GET)
	@ApiOperation("最新APP版本查询")
	public Object findNewAppVersion(@RequestParam("appId")Long appId){
		return appVersionService.findNewAppVersion(appId);
	}


}