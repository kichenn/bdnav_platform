package com.bdxh.user.controller;

import com.bdxh.user.service.VisitLogsService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;

/**
* @Description: 学生网站访问日志控制器
* @Author Kang
* @Date 2019-04-17 17:29:24
*/
@Controller
@RequestMapping("/visitLogsController")
@Slf4j
@Validated
@Api(value = "学生网站访问日志控制器", tags = "学生网站访问日志控制器")
public class VisitLogsController {

	@Autowired
	private VisitLogsService visitLogsService;

}