package com.bdxh.weixiao.controller.pay;

import com.bdxh.common.base.constant.WxAuthorizedConstants;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.pay.dto.WxPayJsOrderDto;
import com.bdxh.pay.feign.WechatJsPayControllerClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


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
    @ApiOperation(value = "根据微信code返回授权信息", response = String.class)
    public Object auth(@RequestParam("code") String code) {
        return WrapMapper.ok(wechatJsPayControllerClient.auth(code).getResult());
    }

    /**
     * @Description: redirectUri回调地址
     * @Author: Kang
     * @Date: 2019/6/17 19:12
     */
    @RequestMapping(value = "/getWechatUrl", method = RequestMethod.GET)
    @ApiOperation(value = "返回微信支付授权地址信息", response = String.class)
    public Object getWechatUrl(@RequestParam("redirectUri") String redirectUri) {
        return WrapMapper.ok(wechatJsPayControllerClient.getWechatUrl(redirectUri).getResult());
    }
}
