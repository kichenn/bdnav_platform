package com.bdxh.servicepermit.feign;

import com.bdxh.servicepermit.dto.*;
import com.bdxh.servicepermit.enums.ServiceProductEnum;
import com.bdxh.servicepermit.fallback.ServiceUserControllerClientFallback;
import com.bdxh.servicepermit.vo.ServiceUserVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.servicepermit.entity.ServiceUser;
import com.github.pagehelper.PageInfo;

import java.util.List;

@Service
@FeignClient(value = "servicepermit-provider-cluster", fallback = ServiceUserControllerClientFallback.class)
public interface ServiceUserControllerClient {


    @RequestMapping(value = "/serviceUser/createService", method = RequestMethod.POST)
    @ResponseBody
    Wrapper createServiceUser(@RequestBody AddServiceUserDto addServiceUserDto);


    @RequestMapping(value = "/serviceUser/findServicePermitAll", method = RequestMethod.GET)
    @ResponseBody
    Wrapper<List<ServiceUser>> findServicePermitAll();

    /**
     * @Description: 鉴定是否有试用资格
     * @Author: Kang
     * @Date: 2019/6/13 16:50
     */
    @RequestMapping(value = "/serviceUser/findServicePermitByCondition", method = RequestMethod.GET)
    @ResponseBody
    Wrapper findServicePermitByCondition(@RequestParam("schoolCode") String schoolCode, @RequestParam("studentCardNumber") String studentCardNumber, @RequestParam("familyCardNumber") String familyCardNumber,@RequestParam("productId") Long productId);

    /**
     * @Description: 领取试用服务许可资格
     * @Author: Kang
     * @Date: 2019/6/13 16:50
     */
    @RequestMapping(value = "/serviceUser/createOnTrialService", method = RequestMethod.POST)
    @ResponseBody
    Wrapper createOnTrialService(@RequestBody AddNoTrialServiceUserDto addNoTrialServiceUserDto);

    /**
     * @Description: 购买服务许可资格
     * @Author: Kang
     * @Date: 2019/6/21 9:32
     */
    @RequestMapping(value = "/serviceUser/createPayService", method = RequestMethod.POST)
    @ResponseBody
    Wrapper createPayService(@RequestBody AddPayServiceUserDto addPayServiceUserDto);

    /**
     * @Description: 领取试用服务许可资格
     * @Author: Kang
     * @Date: 2019/6/13 16:50
     */
    @RequestMapping(value = "/serviceUser/updateServiceUser", method = RequestMethod.POST)
    @ResponseBody
    Wrapper updateServiceUser(@RequestBody ModifyServiceUserDto modifyServiceUserDto);

    @RequestMapping(value = "/serviceUser/deleteService", method = RequestMethod.GET)
    @ResponseBody
    Wrapper deleteService(@RequestParam(name = "schoolCode") String schoolCode,
                          @RequestParam(name = "cardNumber") String cardNumber,
                          @RequestParam(name = "id") Long id);


    @RequestMapping(value = "/serviceUser/queryServiceUser", method = RequestMethod.POST)
    @ResponseBody
    Wrapper<PageInfo<ServiceUserVo>> queryServiceUser(@RequestBody QueryServiceUserDto queryServiceUsedDto);

}
