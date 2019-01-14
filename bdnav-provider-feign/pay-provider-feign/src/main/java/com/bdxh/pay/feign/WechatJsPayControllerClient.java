package com.bdxh.pay.feign;

import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.pay.dto.WxPayJsOrderDto;
import com.bdxh.pay.fallback.WechatJsPayControllerFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @description: 微信JS支付feign客户端
 * @author: xuyuan
 * @create: 2019-01-14 11:20
 **/
@Service
@FeignClient(value = "pay-provider-cluster", fallback = WechatJsPayControllerFallback.class)
public interface WechatJsPayControllerClient {

    /**
     * JS统一下单接口
     * @param wxPayJsOrderDto
     * @return
     * @throws Exception
     */
    @RequestMapping("/wechatJsPay/order")
    @ResponseBody
    Wrapper wechatJsPayOrder(@RequestBody WxPayJsOrderDto wxPayJsOrderDto);

    /**
     * 微信授权接口
     * @param code
     * @return
     */
    @RequestMapping("/wechatJsPay/auth")
    @ResponseBody
    Wrapper auth(@RequestParam("code") String code);

}
