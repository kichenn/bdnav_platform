package com.bdxh.pay.fallback;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.common.wechatpay.js.domain.JsOrderResponse;
import com.bdxh.pay.dto.WxPayJsOrderDto;
import com.bdxh.pay.feign.WechatJsPayControllerClient;
import org.springframework.stereotype.Component;

/**
 * @Description: 微信JS支付hystrix降级服务
 * @Author: Kang
 * @Date: 2019/6/18 9:43
 */
@Component
public class WechatJsPayControllerFallback implements WechatJsPayControllerClient {

    @Override
    public Wrapper wechatJsPayOrder(WxPayJsOrderDto wxPayJsOrderDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper auth(String code) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper getWechatUrl(String redirectUri) {
        return WrapMapper.error();
    }

}
