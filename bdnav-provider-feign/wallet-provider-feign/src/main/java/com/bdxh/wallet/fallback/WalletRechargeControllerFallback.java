package com.bdxh.wallet.fallback;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.wallet.dto.AddWalletRechargeDto;
import com.bdxh.wallet.dto.ModifyWalletRechargeDto;
import com.bdxh.wallet.entity.WalletRecharge;
import com.bdxh.wallet.feign.WalletRechargeControllerClient;
import org.springframework.stereotype.Component;

/**
 * @Description: 消费记录熔断
 * @Author: Kang
 * @Date: 2019/7/15 14:44
 */
@Component
public class WalletRechargeControllerFallback implements WalletRechargeControllerClient {
    @Override
    public Wrapper<String> addWalletRecharge(AddWalletRechargeDto addWalletRechargeDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<Boolean> modifyWalletRechargeByOrderNo(ModifyWalletRechargeDto modifyWalletRechargeDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<WalletRecharge> findWalletRechargeByOrderNo(Long orderNo) {
        return WrapMapper.error();
    }
}
