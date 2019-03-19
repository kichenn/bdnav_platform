package com.bdxh.wallet.service;

import com.bdxh.common.support.IService;
import com.bdxh.wallet.dto.WalletKailuOrderDto;
import com.bdxh.wallet.entity.WalletKailuConsumer;
import com.bdxh.wallet.vo.WalletKailuOrderVo;

import java.util.List;
import java.util.Map;

/**
 * @description: 凯路消费service
 * @author: xuyuan
 * @create: 2019-01-25 16:22
 **/
public interface WalletKailuConsumerService extends IService<WalletKailuConsumer> {

    /**
     * 凯路消费下单
     * @param walletKailuOrderDto
     */
    WalletKailuOrderVo kailuOrder(WalletKailuOrderDto walletKailuOrderDto);

    /**
     * 查询支付失败订单
     * @param param
     * @return
     */
    List<WalletKailuConsumer> getFailForTask(Map<String,Object> param);

    /**
     * 查询超时的订单
     * @param param
     * @return
     */
    List<WalletKailuConsumer> getTimeOutForTask(Map<String,Object> param);

}
