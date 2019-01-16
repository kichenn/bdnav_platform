package com.bdxh.wallet.configration.rocketmq;

import com.bdxh.common.base.enums.RocketMqTransStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.common.message.Message;
import org.springframework.stereotype.Component;

/**
 * @description: 发送事务消息listener示例
 * @author: xuyuan
 * @create: 2019-01-15 15:05
 **/
@Component
@Slf4j
public class RocketMqExampleTransactionListener extends RocketMqTransactionListener {

    @Override
    public LocalTransactionState executeLocalTransaction(Message message, Object o) {
        String transactionId = message.getTransactionId();
        try {
            //执行本地事务
            rocketMqTransUtil.putTransState(transactionId, RocketMqTransStatusEnum.COMMIT_MESSAGE);
            return LocalTransactionState.COMMIT_MESSAGE;
        }catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage(),e.getStackTrace());
            rocketMqTransUtil.putTransState(transactionId, RocketMqTransStatusEnum.ROLLBACK_MESSAGE);
            return LocalTransactionState.ROLLBACK_MESSAGE;
        }
    }

}
