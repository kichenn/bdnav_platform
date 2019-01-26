package com.bdxh.wallet.service;

import com.bdxh.common.web.support.IService;
import com.bdxh.wallet.dto.WalletKailuOrderDto;
import com.bdxh.wallet.entity.WalletKailuConsumer;
import com.bdxh.wallet.vo.WalletKailuOrderVo;

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

}
