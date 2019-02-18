package com.bdxh.wallet.persistence;

import com.bdxh.wallet.entity.WalletKailuConsumer;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

@Repository
public interface WalletKailuConsumerMapper extends Mapper<WalletKailuConsumer> {

    List<WalletKailuConsumer> getFailForTask(Map<String,Object> param);

    List<WalletKailuConsumer> getTimeOutForTask(Map<String,Object> param);

}