package com.bdxh.pay.feign;

import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.pay.dto.WxPayAppOrderDto;
import com.bdxh.pay.fallback.WechatAppPayControllerFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @description: 微信APP支付feign客户端
 * @author: xuyuan
 * @create: 2019-01-14 11:19
 **/
@Service
@FeignClient(value = "pay-provider-cluster", fallback = WechatAppPayControllerFallback.class)
public interface WechatAppPayControllerClient {

    /**
     * 微信APP支付统一下单接口
     * @param wxPayAppOrderDto
     */
    @RequestMapping("/wechatAppPay/order")
    @ResponseBody
    Wrapper wechatAppPayOrder(@RequestBody WxPayAppOrderDto wxPayAppOrderDto);

}
