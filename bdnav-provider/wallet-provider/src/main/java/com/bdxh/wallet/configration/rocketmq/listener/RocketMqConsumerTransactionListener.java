package com.bdxh.wallet.configration.rocketmq.listener;

import com.alibaba.fastjson.JSONObject;
import com.bdxh.common.utils.SnowflakeIdWorker;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @Description: rocketMqConsumer 消费者监听器
 * @Author: Kang
 * @Date: 2019/7/10 17:48
 */
@Slf4j
@Component
public class RocketMqConsumerTransactionListener implements MessageListenerConcurrently {

    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    /**
     * @Description: 消息监听
     * @Author: Kang
     * @Date: 2019/4/28 18:37
     */
    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
        try {
            for (MessageExt msg : msgs) {
                String topic = msg.getTopic();
                String msgBody = new String(msg.getBody(), "utf-8");
                String tags = msg.getTags();
                JSONObject json = JSONObject.parseObject(msgBody);
                log.info("收到消息:,topic:{}, tags:{},msg:{}", topic, tags, msgBody);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
