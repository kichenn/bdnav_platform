package com.bdxh.servicepermit.feign;

import com.bdxh.servicepermit.fallback.ServiceUserControllerClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMethod;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.servicepermit.dto.AddServiceUserDto;
import com.bdxh.servicepermit.dto.ModifyServiceUserDto;
import com.bdxh.servicepermit.dto.QueryServiceUserDto;
import com.bdxh.servicepermit.entity.ServiceUser;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Service
@FeignClient(value = "servicepermit-provider-cluster", fallback = ServiceUserControllerClientFallback.class)
public interface ServiceUserControllerClient {


        @RequestMapping(value = "/serviceUsed/deleteService", method = RequestMethod.GET)
        Wrapper deleteService(@RequestParam(name = "schoolCode") String schoolCode,
                            @RequestParam(name = "cardNumber") Long cardNumber,
                            @RequestParam(name = "id") Long id);


        @RequestMapping(value = "/serviceUsed/queryServiceUser",method = RequestMethod.POST)
        Wrapper<PageInfo<ServiceUser>> queryServiceUser(@RequestBody QueryServiceUserDto queryServiceUsedDto);


        @RequestMapping(value = "/serviceUsed/updateService",method = RequestMethod.POST)
        Wrapper updateServiceUser(@RequestBody ModifyServiceUserDto modifyServiceUserDto);

        @RequestMapping(value = "/serviceUsed/createService",method = RequestMethod.POST)
        Wrapper createServiceUser(@RequestBody AddServiceUserDto addServiceUserDto);



}
