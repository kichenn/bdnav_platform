package com.bdxh.weixiao.controller.pay;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.pay.dto.WxPayJsOrderDto;
import com.bdxh.pay.feign.WechatJsPayControllerClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/wechatJsPayWeiXiaoWeb")
@Api(value = "JSAPI微校支付", tags = "JSAPI微校支付交互API")
@Validated
@Slf4j
public class WecharJsPay {

    @Autowired
    private WechatJsPayControllerClient wechatJsPayControllerClient;

    @RequestMapping(value = "/order", method = RequestMethod.POST)
    @ApiOperation(value = "JS统一下单接口", response = String.class)
    public Object wechatJsPayOrder(@Validated @RequestBody WxPayJsOrderDto wxPayJsOrderDto) {
        return WrapMapper.ok(wechatJsPayControllerClient.wechatJsPayOrder(wxPayJsOrderDto).getResult());
    }

    @RequestMapping(value = "/auth", method = RequestMethod.GET)
    @ApiOperation(value = "微信授权接口", response = String.class)
    public Object auth(@RequestParam("code") String code) {
        return WrapMapper.ok(wechatJsPayControllerClient.auth(code).getResult());
    }
}
