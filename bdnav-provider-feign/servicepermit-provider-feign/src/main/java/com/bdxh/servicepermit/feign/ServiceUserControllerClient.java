package com.bdxh.servicepermit.feign;

import com.bdxh.servicepermit.fallback.ServiceUserControllerClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.servicepermit.dto.AddServiceUserDto;
import com.bdxh.servicepermit.dto.ModifyServiceUserDto;
import com.bdxh.servicepermit.dto.QueryServiceUserDto;
import com.bdxh.servicepermit.entity.ServiceUser;
import com.github.pagehelper.PageInfo;

import java.util.List;

@Service
@FeignClient(value = "servicepermit-provider-cluster", fallback = ServiceUserControllerClientFallback.class)
public interface ServiceUserControllerClient {


        @RequestMapping(value = "/serviceUser/deleteService", method = RequestMethod.GET)
        Wrapper deleteService(@RequestParam(name = "schoolCode") String schoolCode,
                            @RequestParam(name = "cardNumber") Long cardNumber,
                            @RequestParam(name = "id") Long id);


        @RequestMapping(value = "/serviceUser/queryServiceUser",method = RequestMethod.POST)
        Wrapper<PageInfo<ServiceUser>> queryServiceUser(@RequestBody QueryServiceUserDto queryServiceUsedDto);


        @RequestMapping(value = "/serviceUser/updateService",method = RequestMethod.POST)
        Wrapper updateServiceUser(@RequestBody ModifyServiceUserDto modifyServiceUserDto);

        @RequestMapping(value = "/serviceUser/createService",method = RequestMethod.POST)
        @ResponseBody
        Wrapper createServiceUser(@RequestBody AddServiceUserDto addServiceUserDto);



        @RequestMapping(value = "/serviceUser/queryAllServiceUser",method = RequestMethod.GET)
        @ResponseBody
        Wrapper<List<ServiceUser>> queryAllServiceUser(@RequestParam("cardNumber")String cardNumber,
                                                       @RequestParam("schoolCode")String schoolCode,
                                                       @RequestParam("studentNumber")String studentNumber);


}
