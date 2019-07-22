package com.bdxh.wallet.fallback;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.wallet.dto.*;
import com.bdxh.wallet.entity.WalletAccount;
import com.bdxh.wallet.feign.WalletAccountControllerClient;
import com.bdxh.wallet.vo.MyWalletVo;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: Kang
 * @Date: 2019/7/15 17:18
 */
@Component
public class WalletAccountControllerFallback implements WalletAccountControllerClient {

    @Override
    public Wrapper<WalletAccount> findWalletByCardNumberAndSchoolCode(String cardNumber, String schoolCode) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper createWallet(CreateWalletDto createWalletDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<MyWalletVo> myWallet(WalletAccount walletAccountParam) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper findPayPwd(String schoolCode, String cardNumber) {
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
    public Wrapper findNoPwdPay(String schoolCode, String cardNumber) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper rechargeWalletAccount(ModifyAccountMoeny modifyAccountMoeny) {
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
    public Wrapper delWalletAccount(String schoolCode, String cardNumber, Long id) {
        return WrapMapper.error();
    }
}
