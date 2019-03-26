package com.bdxh.onecard.configration.rocketmq;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionCheckListener;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @description: rocketmq本地事务回查类
 * @author: xuyuan
 * @create: 2019-01-29 15:47
 **/
@Component
@Slf4j
public class RocketMqTransactionCheckListener implements TransactionCheckListener {

    @Autowired
    private RocketMqTransUtil rocketMqTransUtil;

    @Override
    public LocalTransactionState checkLocalTransactionState(MessageExt messageExt) {
        String transactionId = messageExt.getTransactionId();
        //查询事务执行结果
        LocalTransactionState transState = rocketMqTransUtil.getTransState(transactionId);
        rocketMqTransUtil.removeTransState(transactionId);
        log.info("事务回查："+transactionId+" 结果 "+transState.name());
        return transState;
    }
}
