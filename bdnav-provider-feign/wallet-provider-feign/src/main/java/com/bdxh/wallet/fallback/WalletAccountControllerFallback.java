package com.bdxh.wallet.fallback;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.wallet.dto.*;
import com.bdxh.wallet.entity.WalletAccount;
import com.bdxh.wallet.feign.WalletAccountControllerClient;
import com.bdxh.wallet.vo.MyWalletVo;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Component;

@Component
public class WalletAccountControllerFallback implements WalletAccountControllerClient {
    @Override
    public Wrapper<MyWalletVo> myWallet(String cardNumber, String schoolCode) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper setPayPwd(SetPayPwdDto setPayPwdDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper modifyPayPwd(ModifyPayPwdDto modifyPayPwdDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper forgetPayPwd(ForgetPayPwdDto forgetPayPwdDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper forgetPayPwdSendCode(String phone) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper setNoPwdPay(SetNoPwdPayPwdDto setNoPwdPayPwdDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<PageInfo<WalletAccount>> findWalletAccountInCondition(QueryWalletAccount queryWalletAccount) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper addWalletAccount(AddWalletAccount addWalletAccount) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper modifyWalletAccount(ModifyWalletAccount modifyWalletAccount) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper delWalletAccount(String schoolCode,String cardNumber,Long id) {
        return WrapMapper.error();
    }
}
