package com.bdxh.school.configration.rocketmq.util;

import com.bdxh.common.base.constant.RocketMqConstrants;
import com.bdxh.common.base.enums.RocketMqTransStatusEnum;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @Description: RocketMq事务回查工具类
 * @Author: Kang
 * @Date: 2019/4/29 11:51
 */
@Component
public class RocketMqTransUtil {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void putTransState(String transactionId, RocketMqTransStatusEnum rocketMqTransStatusEnum) {
        redisTemplate.opsForValue().set(RocketMqConstrants.TRANSACTION_REDIS_PREFIX + transactionId, rocketMqTransStatusEnum.getCode(), 1, TimeUnit.DAYS);
    }

    public LocalTransactionState getTransState(String transactionId) {
        String status = (String) redisTemplate.opsForValue().get(RocketMqConstrants.TRANSACTION_REDIS_PREFIX + transactionId);
        if (StringUtils.equals(status, RocketMqTransStatusEnum.COMMIT_MESSAGE.getCode())) {
            return LocalTransactionState.COMMIT_MESSAGE;
        }
        if (StringUtils.equals(status, RocketMqTransStatusEnum.ROLLBACK_MESSAGE.getCode())) {
            return LocalTransactionState.ROLLBACK_MESSAGE;
        }
        if (StringUtils.equals(status, RocketMqTransStatusEnum.UNKNOW.getCode())) {
            return LocalTransactionState.UNKNOW;
        }
        return LocalTransactionState.UNKNOW;
    }

    public void removeTransState(String transactionId) {
        redisTemplate.delete(RocketMqConstrants.TRANSACTION_REDIS_PREFIX + transactionId);
    }

}
