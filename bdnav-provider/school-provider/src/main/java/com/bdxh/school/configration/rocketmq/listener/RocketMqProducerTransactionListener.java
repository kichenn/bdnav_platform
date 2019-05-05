package com.bdxh.school.configration.rocketmq.listener;

import com.bdxh.common.base.enums.RocketMqTransStatusEnum;
import com.bdxh.school.configration.rocketmq.util.RocketMqTransUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @Description: rocketmqProducer 生产者监听器
 * @Author: Kang
 * @Date: 2019/4/28 14:23
 */
@Slf4j
@Component
public class RocketMqProducerTransactionListener implements TransactionListener {

    @Autowired
    protected RocketMqTransUtil rocketMqTransUtil;

    /**
     * rocketMq发送事务消息
     */
    @Override
    public LocalTransactionState executeLocalTransaction(Message message, Object o) {
        String transactionId = message.getTransactionId();
        try {
            //执行本地事务
//            rocketMqTransUtil.putTransState(transactionId, RocketMqTransStatusEnum.UNKNOW);
            log.info("executeLocalTransaction..................开始发送事务消息:rocketMqTransUtil:{}", rocketMqTransUtil);
            rocketMqTransUtil.putTransState(transactionId, RocketMqTransStatusEnum.COMMIT_MESSAGE);
            return LocalTransactionState.COMMIT_MESSAGE;
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(), e.getStackTrace());
            rocketMqTransUtil.putTransState(transactionId, RocketMqTransStatusEnum.ROLLBACK_MESSAGE);
            return LocalTransactionState.ROLLBACK_MESSAGE;
        }
    }


    /**
     * rocketMq事务回查方法
     */
    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
        String transactionId = messageExt.getTransactionId();
        //查询事务执行结果
        LocalTransactionState transState = rocketMqTransUtil.getTransState(transactionId);
        rocketMqTransUtil.removeTransState(transactionId);
        log.info("事务回查：{} ,结果:{} ", transactionId, transState.name());
        return transState;
    }


}
