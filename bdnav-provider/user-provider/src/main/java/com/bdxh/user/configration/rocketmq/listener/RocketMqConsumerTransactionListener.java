/*
package com.bdxh.user.configration.rocketmq.listener;

import com.bdxh.common.base.constant.RocketMqConstrants;
import com.bdxh.user.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.List;

*/
/**
 * @Description: rocketMqConsumer 消费者监听器
 * @Author: bin
 * @Date: 2019/4/29 126:16
 *//*

@Slf4j
@Component
public class RocketMqConsumerTransactionListener implements MessageListenerConcurrently {


    */
/**
     * @Description: 消息监听
     * @Author: Kang
     * @Date: 2019/4/28 18:37
     *//*

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
        try {
            for (MessageExt msg : msgs) {
                String topic = msg.getTopic();
                String msgBody = new String(msg.getBody(), "utf-8");
                String tags = msg.getTags();
                if(topic.equals(RocketMqConstrants.Topic.schoolOrganizationTopic)){
                    if(StringUtils.isNotEmpty(tags)&&tags.equals(RocketMqConstrants.Tags.schoolOrganizationTag_dept)){

                    }
                    else if(StringUtils.isNotEmpty(tags)&&tags.equals(RocketMqConstrants.Tags.schoolOrganizationTag_class)){

                    }
                }else if(topic.equals(RocketMqConstrants.Topic.bdxhTopic)){

                }
                log.info("收到消息:,topic:{}, tags:{},msg:{}", topic, tags, msgBody);
                msg.getTags();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
*/
