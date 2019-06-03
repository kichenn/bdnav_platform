package com.bdxh.servicepermit.feign;

import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.servicepermit.dto.AddServiceRoleDto;
import com.bdxh.servicepermit.dto.AddServiceRolePermitDto;
import com.bdxh.servicepermit.dto.ModifyServiceRoleDto;
import com.bdxh.servicepermit.dto.ServiceRoleQueryDto;
import com.bdxh.servicepermit.fallback.ServiceRoleControllerClientFallback;
import com.bdxh.servicepermit.fallback.ServiceRolePermitControllerClientFallback;
import com.bdxh.servicepermit.vo.ServiceRolePermitInfoVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description: 服务权限许可信息
 * @Author: Kang
 * @Date: 2019/6/3 17:10
 */
@Service
@FeignClient(value = "servicepermit-provider-cluster", fallback = ServiceRolePermitControllerClientFallback.class)
public interface ServiceRolePermitControllerClient {


    //根据服务许可id删除服务角色权限
    @RequestMapping(value = "/serviceRolePermit/delServiceRolePermitByServiceUserId", method = RequestMethod.GET)
    @ResponseBody
    Wrapper delServiceRolePermitByServiceUserId(@RequestParam("serviceUserId") Long serviceUserId);

    //删除服务权限角色
    @RequestMapping(value = "/serviceRolePermit/addServiceRolePermitInfo", method = RequestMethod.POST)
    @ResponseBody
    Wrapper addServiceRolePermitInfo(@Validated @RequestBody AddServiceRolePermitDto addServiceRolePermitDto);

    //家长id查询 服务权限许可信息（一个家长有多个孩子）
    @RequestMapping(value = "/serviceRolePermit/findServiceRolePermitInfoVo", method = RequestMethod.GET)
    @ResponseBody
    Wrapper<List<ServiceRolePermitInfoVo>> findServiceRolePermitInfoVo(@RequestParam("familyCardNumber") String familyCardNumber);


}
