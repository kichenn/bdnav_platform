package com.bdxh.appmarket.controller;

import com.bdxh.appmarket.service.AppVersionService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;

/**
* @Description: 控制器
* @Author Kang
* @Date 2019-05-14 12:15:05
*/
@Controller
@RequestMapping("/appVersion")
@Slf4j
@Validated
@Api(value = "APP版本控制器控制器", tags = "APP版本控制器控制器")
public class AppVersionController {

	@Autowired
	private AppVersionService appVersionService;

}