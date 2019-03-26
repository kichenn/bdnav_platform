package com.bdxh.onecard.configration.rocketmq;

import com.bdxh.common.base.constant.RocketMqConstrants;
import com.bdxh.common.base.enums.RocketMqTransStatusEnum;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @description: RocketMq事务回查工具类
 * @author: xuyuan
 * @create: 2019-01-15 10:59
 **/
@Component
public class RocketMqTransUtil {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    /**
     * 设置本地事务状态
     * @param transactionId
     * @param rocketMqTransStatusEnum
     */
    public void putTransState(String transactionId, RocketMqTransStatusEnum rocketMqTransStatusEnum){
        redisTemplate.opsForValue().set(RocketMqConstrants.TRANSACTION_REDIS_PREFIX+transactionId,rocketMqTransStatusEnum.getCode(),1, TimeUnit.DAYS);
    }

    /**
     * 获取本地事务状态
     * @param transactionId
     * @return
     */
    public LocalTransactionState getTransState(String transactionId){
        String status=(String) redisTemplate.opsForValue().get(RocketMqConstrants.TRANSACTION_REDIS_PREFIX+transactionId);
        if (StringUtils.equals(status, RocketMqTransStatusEnum.COMMIT_MESSAGE.getCode())){
            return LocalTransactionState.COMMIT_MESSAGE;
        }
        if (StringUtils.equals(status, RocketMqTransStatusEnum.ROLLBACK_MESSAGE.getCode())){
            return LocalTransactionState.ROLLBACK_MESSAGE;
        }
        if (StringUtils.equals(status, RocketMqTransStatusEnum.UNKNOW.getCode())){
            return LocalTransactionState.UNKNOW;
        }
        return LocalTransactionState.UNKNOW;
    }

    public void removeTransState(String transactionId){
        redisTemplate.delete(RocketMqConstrants.TRANSACTION_REDIS_PREFIX+transactionId);
    }

}
