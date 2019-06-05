package com.bdxh.servicepermit.contoller;

import com.bdxh.common.utils.SnowflakeIdWorker;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.servicepermit.dto.AddServiceRoleDto;
import com.bdxh.servicepermit.dto.AddServiceRolePermitDto;
import com.bdxh.servicepermit.dto.ModifyServiceRoleDto;
import com.bdxh.servicepermit.dto.ServiceRoleQueryDto;
import com.bdxh.servicepermit.entity.ServiceRole;
import com.bdxh.servicepermit.service.ServiceRolePermitService;
import com.bdxh.servicepermit.service.ServiceRoleService;
import com.bdxh.servicepermit.vo.ServiceRolePermitInfoVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @Description: 服务许可角色权限关系controller
 * @Author: Kang
 * @Date: 2019/6/1 15:51
 */
@RestController
@RequestMapping("/serviceRolePermit")
@Slf4j
@Validated
@Api(value = "服务许可角色权限关系", tags = "服务许可角色权限关系交互信息")
public class ServiceRolePermitController {

    @Autowired
    private ServiceRoleService serviceRoleService;

    @Autowired
    private ServiceRolePermitService serviceRolePermitService;

    @ApiOperation(value = "根据服务许可id删除服务角色权限", response = Boolean.class)
    @RequestMapping(value = "/delServiceRolePermitByServiceUserId", method = RequestMethod.GET)
    public Object delServiceRolePermitByServiceUserId(@RequestParam("serviceUserId") Long serviceUserId) {
        return WrapMapper.ok(serviceRolePermitService.delServiceRolePermitByServiceUserId(serviceUserId));
    }

    @ApiOperation(value = "增加服务许可角色权限信息", response = Boolean.class)
    @RequestMapping(value = "/addServiceRolePermitInfo", method = RequestMethod.POST)
    public Object addServiceRolePermitInfo(@Validated @RequestBody AddServiceRolePermitDto addServiceRolePermitDto) {
        return WrapMapper.ok(serviceRolePermitService.addServiceRolePermitInfo(addServiceRolePermitDto));
    }


    @ApiOperation(value = "家长id查询 服务权限许可信息（一个家长有多个孩子）", response = Boolean.class)
    @RequestMapping(value = "/findServiceRolePermitInfoVo", method = RequestMethod.GET)
    public Object findServiceRolePermitInfoVo(@RequestParam("familyCardNumber") String familyCardNumber) {
        List<ServiceRolePermitInfoVo> serviceRolePermitInfoVoList = serviceRolePermitService.findServiceRolePermitInfoVo(familyCardNumber);
        if (CollectionUtils.isNotEmpty(serviceRolePermitInfoVoList)) {
            serviceRolePermitInfoVoList.forEach(e -> {
                e.setRoleName(serviceRoleService.selectByKey(e.getRoleId()).getName());
            });
        }
        return WrapMapper.ok(serviceRolePermitInfoVoList);
    }

}