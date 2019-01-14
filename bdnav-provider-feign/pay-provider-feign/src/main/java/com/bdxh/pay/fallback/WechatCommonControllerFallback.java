package com.bdxh.pay.fallback;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.pay.feign.WechatCommonControllerClient;
import org.springframework.stereotype.Component;

/**
 * @description: 微信支付公共hystrix降级服务
 * @author: xuyuan
 * @create: 2019-01-14 17:05
 **/
@Component
public class WechatCommonControllerFallback implements WechatCommonControllerClient {

    @Override
    public Wrapper wechatAppPayOrderQuery(String orderNo) {
        return WrapMapper.error();
    }

}
