package com.bdxh.weixiao.controller.servicepermit;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.servicepermit.dto.WeiXiaoAddServiceUserDto;
import com.bdxh.servicepermit.feign.ServiceUserControllerClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: binzh
 * @create: 2019-05-27 16:57
 **/
@Slf4j
@RequestMapping(value = "/servicePermitWeb")
@RestController
@Api(value = "用户服务许可----用户服务许可API", tags = "用户服务许可----用户服务许可API")
@Validated
public class ServicePermitWebController {
    @Autowired
    private ServiceUserControllerClient serviceUserControllerClient;

    @ApiOperation("微校用户服务许可---------家长添加服务许可证试用期")
    @RequestMapping(value = "/addServicePermit",method = RequestMethod.POST)
    public Object addServicePermit(@RequestBody WeiXiaoAddServiceUserDto weiXiaoAddServiceUserDto){
       return serviceUserControllerClient.addServicePermit(weiXiaoAddServiceUserDto);
    }
}