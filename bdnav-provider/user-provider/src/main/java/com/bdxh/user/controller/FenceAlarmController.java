package com.bdxh.user.controller;

import com.bdxh.user.service.FenceAlarmService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;

/**
* @Description: 控制器
* @Author Kang
* @Date 2019-04-17 17:29:24
*/
@Controller
@RequestMapping("/fenceAlarm")
@Slf4j
@Validated
@Api(value = "FenceAlarm控制器", tags = "FenceAlarm")
public class FenceAlarmController {

	@Autowired
	private FenceAlarmService fenceAlarmService;

}