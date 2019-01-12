package com.bdxh.wallet.fallback;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.wallet.feign.WalletControllerClient;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * @description: hystrix降级服务
 * @author: xuyuan
 * @create: 2018-12-27 17:22
 **/
public class WalletControllerFallback implements WalletControllerClient {

    @Override
    public Wrapper addRechargeLog(String schoolCode, Long userId, String userName, String cardNumber ,BigDecimal amount, String orderType, Byte orderStatus) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper changeRechargeLog(Long orderNo, Byte status) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper changeRechargeLog(Long orderNo, Byte status, String thirdOrderNo) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper getLogByOrderNo(Long orderNo) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper updatePaying(Long orderNo, Byte status) {
        return WrapMapper.error();
    }

}
