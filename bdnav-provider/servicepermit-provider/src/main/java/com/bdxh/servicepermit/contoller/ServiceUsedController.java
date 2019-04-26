package com.bdxh.servicepermit.contoller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import com.bdxh.servicepermit.entity.ServiceUsed;
import com.bdxh.servicepermit.service.ServiceUsedService;

/**
* @Description: 控制器
* @Author Kang
* @Date 2019-04-26 10:26:58
*/
@Controller
@RequestMapping("/serviceUsed")
@Slf4j
@Validated
@Api(value = "ServiceUsed控制器", tags = "ServiceUsed")
public class ServiceUsedController {

	@Autowired
	private ServiceUsedService serviceUsedService;

}