package com.bdxh.account.configration.rocketmq.util;

import com.bdxh.account.configration.redis.RedisUtil;
import com.bdxh.common.base.constant.RocketMqConstrants;
import com.bdxh.common.base.enums.RocketMqTransStatusEnum;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Description: RocketMq事务回查工具类
 * @Author: bin
 * @Date: 2019/4/29 126:16
 */
@Component
public class RocketMqTransUtil {

    @Autowired
    private RedisUtil redisUtil;

    public void putTransState(String transactionId, RocketMqTransStatusEnum rocketMqTransStatusEnum) {
        redisUtil.setWithExpireTime(RocketMqConstrants.TRANSACTION_REDIS_PREFIX + transactionId, rocketMqTransStatusEnum.getCode(), 3600 * 24);
    }

    public LocalTransactionState getTransState(String transactionId) {
        String status = (String) redisUtil.getObject(RocketMqConstrants.TRANSACTION_REDIS_PREFIX + transactionId);
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
        redisUtil.delete(RocketMqConstrants.TRANSACTION_REDIS_PREFIX + transactionId);
    }

}
