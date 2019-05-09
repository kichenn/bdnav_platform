package com.bdxh.backend.controller.servicepermit;

import com.bdxh.backend.configration.security.utils.SecurityUtils;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.servicepermit.dto.AddServiceUserDto;
import com.bdxh.servicepermit.dto.ModifyServiceUserDto;
import com.bdxh.servicepermit.dto.QueryServiceUserDto;
import com.bdxh.servicepermit.feign.ServiceUserControllerClient;
import com.bdxh.system.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/ServiceUserWeb")
@Validated
@Slf4j
@Api(value = "订单服务许可", tags = "订单服务许可交互API")
public class ServiceUserControllerWeb {

    @Autowired
    private ServiceUserControllerClient serviceUserControllerClient;

    @ApiOperation("根据条件查询订单")
    @RequestMapping(value = "/queryServiceUser", method = RequestMethod.POST)
    public Object queryServiceUser(@Validated @RequestBody QueryServiceUserDto queryServiceUsedDto, BindingResult bindingResult) {
        Wrapper wrapper = serviceUserControllerClient.queryServiceUser(queryServiceUsedDto);
        return wrapper;

    }


    @ApiOperation("删除订单服务")
    @RequestMapping(value = "/deleteService", method = RequestMethod.GET)
    public Object deleteService(@RequestParam("schoolCode") @NotNull(message = "schoolCode不能为空") String schoolCode,
                                @RequestParam("cardNumber") @NotNull(message = "userId不能为空") Long cardNumber,
                                @RequestParam(name = "id") @NotNull(message = "订单id不能为空") Long id) {
        Wrapper wrapper = serviceUserControllerClient.deleteService(schoolCode, cardNumber, id);
        return wrapper;
    }

    @ApiOperation("添加订单服务许可")
    @RequestMapping(value = "/createServiceUser", method = RequestMethod.POST)
    public Object createServiceUser(@Validated @RequestBody AddServiceUserDto addServiceUserDto, BindingResult bindingResult) {
        User user = SecurityUtils.getCurrentUser();
        addServiceUserDto.setOperator(user.getId());
        addServiceUserDto.setOperatorName(user.getUserName());
        Wrapper wrapper = serviceUserControllerClient.createServiceUser(addServiceUserDto);
        return wrapper;
    }


    @ApiOperation("更新订单服务许可")
    @RequestMapping(value = "/updateServiceUser", method = RequestMethod.POST)
    public Object updateServiceUser(@Validated @RequestBody ModifyServiceUserDto modifyServiceUserDto, BindingResult bindingResult) {
        User user = SecurityUtils.getCurrentUser();
        modifyServiceUserDto.setOperator(user.getId());
        modifyServiceUserDto.setOperatorName(user.getUserName());
        Wrapper wrapper = serviceUserControllerClient.updateServiceUser(modifyServiceUserDto);
        return wrapper;
    }
}
