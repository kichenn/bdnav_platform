package com.bdxh.pay.feign;

import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.pay.fallback.WechatCommonControllerFallback;
import com.bdxh.pay.vo.WechatOrderQueryVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description: 微信支付公共feign客户端
 * @Author: Kang
 * @Date: 2019/6/17 10:26
 */
@Service
@FeignClient(value = "pay-provider-cluster", fallback = WechatCommonControllerFallback.class)
public interface WechatCommonControllerClient {

    /**
     * 微信APP支付订单查询接口
     *
     * @param orderNo
     */
    @RequestMapping("/wechatCommonPay/query")
    @ResponseBody
    Wrapper<WechatOrderQueryVo> wechatAppPayOrderQuery(@RequestParam(name = "orderNo") String orderNo);

}
