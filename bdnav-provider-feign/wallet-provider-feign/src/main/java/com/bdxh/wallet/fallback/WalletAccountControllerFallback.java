package com.bdxh.wallet.fallback;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.wallet.dto.AddWalletAccount;
import com.bdxh.wallet.dto.ModifyWalletAccount;
import com.bdxh.wallet.dto.QueryWalletAccount;
import com.bdxh.wallet.entity.WalletAccount;
import com.bdxh.wallet.feign.WalletAccountControllerClient;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Component;

@Component
public class WalletAccountControllerFallback implements WalletAccountControllerClient {
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
