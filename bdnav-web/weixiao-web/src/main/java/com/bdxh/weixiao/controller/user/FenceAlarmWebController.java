package com.bdxh.weixiao.controller.user;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: binzh
 * @create: 2019-04-23 09:16
 **/
@RestController
@RequestMapping("/fenceAlarm")
@Validated
@Slf4j
@Api(value = "微校平台----学生出入围栏日志API", tags = "微校平台----学生出入围栏日志API")
public class FenceAlarmWebController {
}