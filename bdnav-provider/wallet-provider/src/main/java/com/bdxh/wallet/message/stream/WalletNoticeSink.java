package com.bdxh.wallet.message.stream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * @description: 微信支付回调消息监听通道
 * @author: xuyuan
 * @create: 2019-01-16 10:51
 **/
public interface WalletNoticeSink {

    String INPUT = "walletNotice";

    @Input("walletNotice")
    SubscribableChannel walletNotice();

}
