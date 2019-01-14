package com.bdxh.pay.fallback;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.pay.dto.WxPayAppOrderDto;
import com.bdxh.pay.feign.WechatAppPayControllerClient;
import org.springframework.stereotype.Component;

/**
 * @description: 微信APP支付hystrix降级服务
 * @author: xuyuan
 * @create: 2019-01-14 16:50
 **/
@Component
public class WechatAppPayControllerFallback implements WechatAppPayControllerClient {

    @Override
    public Wrapper wechatAppPayOrder(WxPayAppOrderDto wxPayAppOrderDto) {
        return WrapMapper.error();
    }

}
