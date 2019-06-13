package com.bdxh.servicepermit.contoller;

import com.bdxh.common.utils.SnowflakeIdWorker;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.servicepermit.dto.AddServiceRoleDto;
import com.bdxh.servicepermit.dto.ModifyServiceRoleDto;
import com.bdxh.servicepermit.dto.ServiceRoleQueryDto;
import com.bdxh.servicepermit.entity.ServiceRole;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.bdxh.servicepermit.service.ServiceRoleService;

import java.util.Date;

/**
 * @Description: 服务许可角色API
 * @Author Kang
 * @Date 2019-06-01 10:47:48
 */
@RestController
@RequestMapping("/serviceRole")
@Slf4j
@Validated
@Api(value = "服务许可角色API", tags = "服务许可角色交互信息")
public class ServiceRoleController {

    @Autowired
    private ServiceRoleService serviceRoleService;

    @ApiOperation(value = "新增服务许可角色", response = Boolean.class)
    @RequestMapping(value = "/addServiceRole", method = RequestMethod.POST)
    public Object addServiceRole(@Validated @RequestBody AddServiceRoleDto addServiceRole) {
        try {
            ServiceRole serviceRole = new ServiceRole();
            BeanUtils.copyProperties(addServiceRole, serviceRole);
            serviceRoleService.save(serviceRole);
        } catch (Exception e) {
            if (e instanceof DuplicateKeyException) {
                //角色名称
                return WrapMapper.error("该角色名称已存在，无需再添加！");
            }
            e.printStackTrace();
        }
        return WrapMapper.ok();
    }

    @ApiOperation(value = "删除服务许可角色", response = Boolean.class)
    @RequestMapping(value = "/delServiceRoleById", method = RequestMethod.GET)
    public Object delServiceRoleById(@RequestParam("id") Long id) {
        return WrapMapper.ok(serviceRoleService.deleteByKey(id) > 0);
    }

    @ApiOperation(value = "修改服务许可角色", response = Boolean.class)
    @RequestMapping(value = "/modifyServiceRoleById", method = RequestMethod.POST)
    public Object modifyServiceRoleById(@Validated @RequestBody ModifyServiceRoleDto modifyServiceRoleDto) {


        try {
            ServiceRole serviceRole = new ServiceRole();
            BeanUtils.copyProperties(modifyServiceRoleDto, serviceRole);
            serviceRole.setUpdateDate(new Date());
            serviceRoleService.update(serviceRole);
        } catch (Exception e) {
            if (e instanceof DuplicateKeyException) {
                //角色名称
                return WrapMapper.error("该角色名称已存在，无需再添加！");
            }
            e.printStackTrace();
        }
        return WrapMapper.ok();
    }

    @ApiOperation(value = "查询服务许可角色（分页查询信息）", response = ServiceRole.class)
    @RequestMapping(value = "/queryServiceRole", method = RequestMethod.POST)
    public Object queryServiceRole(@RequestBody ServiceRoleQueryDto serviceRoleQueryDto) {
        return WrapMapper.ok(serviceRoleService.queryServiceRole(serviceRoleQueryDto));
    }
}