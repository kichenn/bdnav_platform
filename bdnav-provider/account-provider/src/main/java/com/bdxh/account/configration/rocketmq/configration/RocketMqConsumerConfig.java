package com.bdxh.account.configration.rocketmq.configration;


import com.bdxh.account.configration.rocketmq.listener.RocketMqConsumerTransactionListener;
import com.bdxh.account.configration.rocketmq.properties.RocketMqConsumerProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: rocketMq 消费者配置
 * Rocketmq消费分为push和pull两种方式，push为被动消费类型，pull为主动消费类型，push方式最终还是会从broker中pull消息。
 * 不同于pull的是，push首先要注册消费监听器，当监听器处触发后才开始消费消息，所以被称为“被动”消费。
 * 默认为发布订阅的消费模式，广播模式 在消费端设置成功广播就行：consumer.setMessageModel(MessageModel.BROADCASTING);
 * @Author: bin
 * @Date: 2019/4/29 126:16
 */
@Configuration
@Slf4j
public class RocketMqConsumerConfig {


    /**
     * 消费者，消费
     *
     * @param rocketMqConsumerProperties
     * @return
     */
    @Bean(value = "defaultMQPushConsumer", destroyMethod = "shutdown")
    @ConditionalOnBean(value = {RocketMqConsumerProperties.class, RocketMqConsumerTransactionListener.class})
    public DefaultMQPushConsumer defaultMQPushConsumer(@Autowired RocketMqConsumerProperties rocketMqConsumerProperties, @Autowired
            RocketMqConsumerTransactionListener rocketMqConsumerTransactionListener) {
        log.info("--------------------defaultMQPushConsumer正在创建-------------------");
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(rocketMqConsumerProperties.getDefaultGroupName());

        //这里设置的是一个consumer的消费策略
        //CONSUME_FROM_LAST_OFFSET 默认策略，从该队列最尾开始消费，即跳过历史消息
        //CONSUME_FROM_FIRST_OFFSET 从队列最开始开始消费，即历史消息（还储存在broker的）全部消费一遍
        //CONSUME_FROM_TIMESTAMP 从某个时间点开始消费，和setConsumeTimestamp()配合使用，默认是半个小时以前
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);

        //设置地址
        consumer.setNamesrvAddr(rocketMqConsumerProperties.getNamesrvAddr());
        //最大线程
        consumer.setConsumeThreadMax(20);
        //消费失败，重试次数10次（如果大于十次，应该在消费中间判断是否，写到响应的日志里）【默认重试16次】
        consumer.setMaxReconsumeTimes(10);
        try {
            consumer.subscribe(rocketMqConsumerProperties.getTopic(), rocketMqConsumerProperties.getTag());
            // 开启监听
            consumer.registerMessageListener(rocketMqConsumerTransactionListener);
            consumer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("--------------------defaultMQPushConsumer server开启成功---------------------------------.");
        return consumer;
    }

}
