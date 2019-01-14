package com.bdxh.pay.fallback;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.pay.dto.WxPayJsOrderDto;
import com.bdxh.pay.feign.WechatJsPayControllerClient;
import org.springframework.stereotype.Component;

/**
 * @description: 微信JS支付hystrix降级服务
 * @author: xuyuan
 * @create: 2019-01-14 17:00
 **/
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

}
