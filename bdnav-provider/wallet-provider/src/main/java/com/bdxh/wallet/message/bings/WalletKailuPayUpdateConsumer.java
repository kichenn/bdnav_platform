package com.bdxh.wallet.message.bings;

import com.alibaba.fastjson.JSON;
import com.bdxh.wallet.entity.WalletKailuConsumer;
import com.bdxh.wallet.message.stream.WalletKailuPayUpdateSink;
import com.bdxh.wallet.service.WalletKailuConsumerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;

/**
 * @description: 凯路消息消费者
 * @author: xuyuan
 * @create: 2019-01-25 10:54
 **/
@EnableBinding(WalletKailuPayUpdateSink.class)
@Slf4j
public class WalletKailuPayUpdateConsumer {

    @Autowired
    private WalletKailuConsumerService walletKailuConsumerService;

    @StreamListener(WalletKailuPayUpdateSink.INPUT)
    public void reciveWalletKailuUpdate(Message<String> message){
        String consumer = message.getPayload();
        log.info("收到凯路订单更新消息："+consumer);
        //更新订单状态
        WalletKailuConsumer walletKailuConsumer = JSON.parseObject(consumer, WalletKailuConsumer.class);
        walletKailuConsumerService.update(walletKailuConsumer);
    }

}
