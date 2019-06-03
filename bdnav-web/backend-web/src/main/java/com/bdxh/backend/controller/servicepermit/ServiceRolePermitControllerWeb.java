package com.bdxh.backend.controller.servicepermit;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.servicepermit.dto.AddServiceRolePermitDto;
import com.bdxh.servicepermit.feign.ServiceRolePermitControllerClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ServiceRolePermitWeb")
@Validated
@Slf4j
@Api(value = "订单服务角色权限许可信息", tags = "订单服务角色权限许可信息交互API")
public class ServiceRolePermitControllerWeb {

    @Autowired
    private ServiceRolePermitControllerClient serviceRolePermitControllerClient;

    @ApiOperation(value = "根据服务许可id删除服务角色权限", response = Boolean.class)
    @RequestMapping(value = "/delServiceRolePermitByServiceUserId", method = RequestMethod.GET)
    public Object delServiceRolePermitByServiceUserId(@RequestParam("serviceUserId") Long serviceUserId) {
        return WrapMapper.ok(serviceRolePermitControllerClient.delServiceRolePermitByServiceUserId(serviceUserId));
    }

    @ApiOperation(value = "增加服务许可角色权限信息", response = Boolean.class)
    @RequestMapping(value = "/addServiceRolePermitInfo", method = RequestMethod.POST)
    public Object addServiceRolePermitInfo(@Validated @RequestBody AddServiceRolePermitDto addServiceRolePermitDto) {
        return WrapMapper.ok(serviceRolePermitControllerClient.addServiceRolePermitInfo(addServiceRolePermitDto));
    }

    @ApiOperation(value = "家长id查询 服务权限许可信息（一个家长有多个孩子）", response = Boolean.class)
    @RequestMapping(value = "/findServiceRolePermitInfoVo", method = RequestMethod.GET)
    public Object findServiceRolePermitInfoVo(@RequestParam("familyCardNumber") String familyCardNumber) {
        return WrapMapper.ok(serviceRolePermitControllerClient.findServiceRolePermitInfoVo(familyCardNumber));
    }
}
