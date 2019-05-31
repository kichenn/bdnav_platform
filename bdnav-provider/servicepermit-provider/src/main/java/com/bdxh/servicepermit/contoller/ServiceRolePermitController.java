package com.bdxh.servicepermit.contoller;

import com.bdxh.servicepermit.service.ServiceRolePermitService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;

/**
* @Description: 控制器
* @Author Kang
* @Date 2019-05-31 11:36:26
*/
@Controller
@RequestMapping("/serviceRolePermit")
@Slf4j
@Validated
@Api(value = "商品服务与角色关系表", tags = "商品服务与角色关系表")
public class ServiceRolePermitController {

	@Autowired
	private ServiceRolePermitService serviceRolePermitService;

}