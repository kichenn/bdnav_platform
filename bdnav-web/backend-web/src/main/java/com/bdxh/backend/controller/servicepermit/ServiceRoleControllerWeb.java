package com.bdxh.backend.controller.servicepermit;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.servicepermit.dto.AddServiceRoleDto;
import com.bdxh.servicepermit.dto.ModifyServiceRoleDto;
import com.bdxh.servicepermit.dto.ServiceRoleQueryDto;
import com.bdxh.servicepermit.entity.ServiceRole;
import com.bdxh.servicepermit.feign.ServiceRoleControllerClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/ServiceRoleWeb")
@Validated
@Slf4j
@Api(value = "订单服务角色", tags = "订单服务角色交互API")
public class ServiceRoleControllerWeb {

    @Autowired
    private ServiceRoleControllerClient serviceRoleControllerClient;

    @ApiOperation(value = "新增服务许可角色", response = Boolean.class)
    @RequestMapping(value = "/addServiceRole", method = RequestMethod.POST)
    public Object addServiceRole(@Validated @RequestBody AddServiceRoleDto addServiceRole) {
        return WrapMapper.ok(serviceRoleControllerClient.addServiceRole(addServiceRole).getResult());
    }

    @ApiOperation(value = "删除服务许可角色", response = Boolean.class)
    @RequestMapping(value = "/delServiceRoleById", method = RequestMethod.GET)
    public Object delServiceRoleById(@RequestParam("id") Long id) {
        return WrapMapper.ok(serviceRoleControllerClient.delServiceRoleById(id).getResult());
    }

    @ApiOperation(value = "修改服务许可角色", response = Boolean.class)
    @RequestMapping(value = "/modifyServiceRoleById", method = RequestMethod.POST)
    public Object modifyServiceRoleById(@Validated @RequestBody ModifyServiceRoleDto modifyServiceRoleDto) {
        return WrapMapper.ok(serviceRoleControllerClient.modifyServiceRoleById(modifyServiceRoleDto).getResult());
    }

    @ApiOperation(value = "查询服务许可角色（分页查询信息）", response = ServiceRole.class)
    @RequestMapping(value = "/queryServiceRole", method = RequestMethod.POST)
    public Object queryServiceRole(@RequestBody ServiceRoleQueryDto serviceRoleQueryDto) {
        return WrapMapper.ok(serviceRoleControllerClient.queryServiceRole(serviceRoleQueryDto).getResult());
    }
}
