package com.bdxh.pay.configration.rocketmq;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @description: rocketmq本地事务执行类
 * @author: xuyuan
 * @create: 2019-01-15 14:58
 **/
@Component
@Slf4j
public abstract class RocketMqTransactionListener implements TransactionListener {

    @Autowired
    protected RocketMqTransUtil rocketMqTransUtil;

    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
        String transactionId = messageExt.getTransactionId();
        //查询事务执行结果
        LocalTransactionState transState = rocketMqTransUtil.getTransState(transactionId);
        rocketMqTransUtil.removeTransState(transactionId);
        log.info("事务回查："+transactionId+" 结果 "+transState.name());
        return transState;
    }

}
