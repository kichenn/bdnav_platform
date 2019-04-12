package com.bdxh.appburied.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import com.bdxh.appburied.service.AppStatusService;
import org.springframework.web.bind.annotation.RestController;

/**
* @Description: 控制器
* @Author Kang
* @Date 2019-04-11 16:39:55
*/
@RestController
@RequestMapping("/appStatus")
@Slf4j
@Validated
@Api(value = "AppStatus控制器", tags = "AppStatus")
public class AppStatusController {

	@Autowired
	private AppStatusService appStatusService;

}