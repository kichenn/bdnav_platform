package com.bdxh.wallet.persistence;

import com.bdxh.wallet.entity.WalletKailuConsumer;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

@Repository
public interface WalletKailuConsumerMapper extends Mapper<WalletKailuConsumer> {

    /**
     * 查询支付失败的订单
     * @param param
     * @return
     */
    List<WalletKailuConsumer> getFailForTask(Map<String,Object> param);

    /**
     * 查询支付超时的订单
     * @param param
     * @return
     */
    List<WalletKailuConsumer> getTimeOutForTask(Map<String,Object> param);

}