package com.bdxh.wallet.persistence;

import com.bdxh.wallet.entity.WalletAccount;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface WalletAccountMapper extends Mapper<WalletAccount> {
}