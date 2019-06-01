package com.bdxh.school.contoller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import com.bdxh.school.entity.SchoolOrg;
import com.bdxh.school.service.SchoolOrgService;

/**
* @Description: 控制器
* @Author Kang
* @Date 2019-05-31 14:06:08
*/
@Controller
@RequestMapping("/schoolOrg")
@Slf4j
@Validated
@Api(value = "SchoolOrg控制器", tags = "SchoolOrg")
public class SchoolOrgController {

	@Autowired
	private SchoolOrgService schoolOrgService;

}