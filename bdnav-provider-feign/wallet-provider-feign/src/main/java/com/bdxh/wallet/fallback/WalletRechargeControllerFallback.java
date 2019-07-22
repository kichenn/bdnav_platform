package com.bdxh.wallet.fallback;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.wallet.dto.AddWalletRechargeDto;
import com.bdxh.wallet.dto.ModifyWalletRechargeDto;
import com.bdxh.wallet.dto.QueryWalletRechargeDto;
import com.bdxh.wallet.entity.WalletRecharge;
import com.bdxh.wallet.feign.WalletRechargeControllerClient;
import com.bdxh.wallet.vo.BaseEchartsVo;
import com.bdxh.wallet.vo.WalletRechargePayVo;
import com.bdxh.wallet.vo.WalletRechargeVo;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Component;

import java.util.List;

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
    public Wrapper<WalletRechargePayVo> findWalletRechargeByOrderNo(Long orderNo) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<PageInfo<WalletRechargeVo>> findWalletRechargeByCondition(QueryWalletRechargeDto queryWalletRechargeDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<List<BaseEchartsVo>> findWalletRechargeTypeMoneySum(String schoolCode) {
        return WrapMapper.error();
    }
}
