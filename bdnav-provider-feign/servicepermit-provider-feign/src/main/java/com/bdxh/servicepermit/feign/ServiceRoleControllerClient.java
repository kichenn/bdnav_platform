package com.bdxh.servicepermit.feign;

import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.servicepermit.dto.*;
import com.bdxh.servicepermit.entity.ServiceRole;
import com.bdxh.servicepermit.entity.ServiceUser;
import com.bdxh.servicepermit.fallback.ServiceRoleControllerClientFallback;
import com.bdxh.servicepermit.fallback.ServiceUserControllerClientFallback;
import com.github.pagehelper.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description: 服务权限角色
 * @Author: Kang
 * @Date: 2019/6/3 17:10
 */
@Service
@FeignClient(value = "servicepermit-provider-cluster", fallback = ServiceRoleControllerClientFallback.class)
public interface ServiceRoleControllerClient {


    //新增服务权限角色
    @RequestMapping(value = "/serviceRole/addServiceRole", method = RequestMethod.POST)
    @ResponseBody
    Wrapper addServiceRole(@Validated @RequestBody AddServiceRoleDto addServiceRole);

    //删除服务权限角色
    @RequestMapping(value = "/serviceRole/delServiceRoleById", method = RequestMethod.GET)
    @ResponseBody
    Wrapper delServiceRoleById(@RequestParam("id") Long id);

    //修改服务许可角色
    @RequestMapping(value = "/serviceRole/modifyServiceRoleById", method = RequestMethod.POST)
    @ResponseBody
    Wrapper modifyServiceRoleById(@Validated @RequestBody ModifyServiceRoleDto modifyServiceRoleDto);

    //查询服务许可角色（分页查询信息）
    @RequestMapping(value = "/serviceRole/queryServiceRole", method = RequestMethod.POST)
    @ResponseBody
    Wrapper<PageInfo<ServiceRole>> queryServiceRole(@RequestBody ServiceRoleQueryDto serviceRoleQueryDto);


}
