package com.bdxh.appmarket.controller;

import com.bdxh.appmarket.dto.*;
import com.bdxh.appmarket.entity.SystemApp;
import com.bdxh.appmarket.service.SystemAppService;
import com.bdxh.appmarket.vo.appVersionVo;
import com.bdxh.common.utils.BeanMapUtils;
import com.bdxh.common.utils.BeanToMapUtil;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;
import java.util.stream.Collectors;

/**
* @Description: 控制器
* @Author Kang
* @Date 2019-06-05 15:05:12
*/
@RestController
@RequestMapping("/systemApp")
@Slf4j
@Validated
@Api(value = "管控应用APP控制器", tags = "管控应用APP控制器")
public class SystemAppController {

	@Autowired
	private SystemAppService systemAppService;

	@ApiOperation("带条件查询管控应用信息")
	@RequestMapping(value = "/findAppControl", method = RequestMethod.POST)
	public Object findAppControl(@Valid @RequestBody QuerySystemAppDto querySystemAppDto, BindingResult bindingResult) {
		//检验参数
		if (bindingResult.hasErrors()) {
			String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
			return WrapMapper.error(errors);
		}
		try {
			Map<String, Object> param = BeanToMapUtil.objectToMap(querySystemAppDto);
			PageInfo<SystemApp> systemApps = systemAppService.findAppControl(param,querySystemAppDto.getPageNum(),querySystemAppDto.getPageSize());
			return WrapMapper.ok(systemApps);
		} catch (Exception e) {
			e.printStackTrace();
			return WrapMapper.error(e.getMessage());
		}
	}


	@ApiOperation("添加管控应用信息")
	@RequestMapping(value = "/addAppCategory",method = RequestMethod.POST)
	public Object addAppCategory(@Valid @RequestBody AddSystemAppDto addSystemAppDto, BindingResult bindingResult){
		//检验参数
		if(bindingResult.hasErrors()){
			String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
			return WrapMapper.error(errors);
		}
		try {
			SystemApp systemApp = BeanMapUtils.map(addSystemAppDto, SystemApp.class);
			systemApp.setSystemApkUrl(addSystemAppDto.getSystemApkUrl()+"&response-content-disposition=attachment");
			Boolean result=systemAppService.save(systemApp)>0;
			return WrapMapper.ok(result);
		}catch (Exception e){
			e.printStackTrace();
			return WrapMapper.error(e.getMessage());
		}
	}



	@ApiOperation("修改管控应用信息")
	@RequestMapping(value = "/modifyAppCategory",method = RequestMethod.POST)
	public Object modifyAppCategory(@Valid @RequestBody ModifySystemAppDto modifySystemAppDto, BindingResult bindingResult){
		//检验参数
		if(bindingResult.hasErrors()){
			String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
			return WrapMapper.error(errors);
		}
		try {
			SystemApp systemApp = BeanMapUtils.map(modifySystemAppDto, SystemApp.class);
			Boolean result=systemAppService.update(systemApp)>0;
			return WrapMapper.ok(result);
		}catch (Exception e){
			e.printStackTrace();
			return WrapMapper.error(e.getMessage());
		}
	}

	@ApiOperation("删除id管控应用信息")
	@RequestMapping(value = "/delSystemAppById", method = RequestMethod.GET)
	public Object delSystemAppById(@RequestParam(name = "id") Long id) {
		try {
			Boolean result=systemAppService.deleteByKey(id)>0;
			return WrapMapper.ok(result);
		} catch (Exception e) {
			e.printStackTrace();
			return WrapMapper.error(e.getMessage());
		}
	}


	@ApiOperation("检查管控版本迭代")
	@RequestMapping(value = "/versionUpdating", method = RequestMethod.GET)
	public Object versionUpdating() {
		try {
            SystemApp systemApp=systemAppService.versionUpdating();
            appVersionVo appVersionVo=new appVersionVo();
			appVersionVo.setAppVersion(systemApp.getAppVersion());
			appVersionVo.setSystemApkUrl(systemApp.getSystemApkUrl());
			return WrapMapper.ok(appVersionVo);
		} catch (Exception e) {
			e.printStackTrace();
			return WrapMapper.error(e.getMessage());
		}
	}


}