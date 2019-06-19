package com.bdxh.pay.feign;

import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.common.wechatpay.js.domain.JsOrderResponse;
import com.bdxh.pay.dto.WxPayJsOrderDto;
import com.bdxh.pay.fallback.WechatJsPayControllerFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

/**
 * @Description: 微信JS支付feign客户端
 * @Author: Kang
 * @Date: 2019/6/17 10:26
 */
@Service
@FeignClient(value = "pay-provider-cluster", fallback = WechatJsPayControllerFallback.class)
public interface WechatJsPayControllerClient {

    /**
     * JS统一下单接口
     *
     * @param wxPayJsOrderDto
     * @return
     * @throws Exception
     */
    @RequestMapping("/wechatJsPay/order")
    @ResponseBody
    Wrapper wechatJsPayOrder(@RequestBody WxPayJsOrderDto wxPayJsOrderDto);

    /**
     * 根据微信code返回授权信息
     *
     * @param code
     * @return
     */
    @RequestMapping("/wechatJsPay/auth")
    @ResponseBody
    Wrapper auth(@RequestParam("code") String code);

    /**
     * @Description: 返回微信支付授权地址信息
     * @Author: Kang
     * @Date: 2019/6/18 9:42
     */
    @RequestMapping(value = "/wechatJsPay/getWechatUrl", method = RequestMethod.GET)
    @ResponseBody
    Wrapper getWechatUrl(@RequestParam("redirectUri") String redirectUri);
}
