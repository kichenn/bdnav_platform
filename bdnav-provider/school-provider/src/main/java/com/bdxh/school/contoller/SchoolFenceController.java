package com.bdxh.school.contoller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import com.bdxh.school.entity.SchoolFence;
import com.bdxh.school.service.SchoolFenceService;

/**
* @Description: 控制器
* @Author Kang
* @Date 2019-04-11 09:56:14
*/
@Controller
@RequestMapping("/schoolFence")
@Slf4j
@Validated
@Api(value = "SchoolFence控制器", tags = "SchoolFence")
public class SchoolFenceController {

	@Autowired
	private SchoolFenceService schoolFenceService;

}