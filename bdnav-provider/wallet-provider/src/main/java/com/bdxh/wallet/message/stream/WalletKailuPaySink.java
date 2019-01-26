package com.bdxh.wallet.message.stream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * @description: 凯路消费消息通道
 * @author: xuyuan
 * @create: 2019-01-25 10:51
 **/
public interface WalletKailuPaySink {

    String INPUT = "walletKailuPay";

    @Input("walletKailuPay")
    SubscribableChannel walletKailu();

}
