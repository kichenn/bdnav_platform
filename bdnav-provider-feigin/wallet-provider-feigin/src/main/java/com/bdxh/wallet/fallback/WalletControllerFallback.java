package com.bdxh.wallet.fallback;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.wallet.feign.WalletControllerClient;

import java.math.BigDecimal;

/**
 * @description: hystrix降级服务
 * @author: xuyuan
 * @create: 2018-12-27 17:22
 **/
public class WalletControllerFallback implements WalletControllerClient {


    @Override
    public Wrapper addRechargeLog(Long userId, BigDecimal amount, String orderType, Byte orderStatus) {
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
}
