package com.bdxh.pay.configration.rocketmq.configration;

import com.bdxh.pay.configration.rocketmq.listener.RocketMqConsumerTransactionListener;
import com.bdxh.pay.configration.rocketmq.properties.RocketMqConsumerProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @Description: rocketMq 消费者配置
 * Rocketmq消费分为push和pull两种方式，push为被动消费类型，pull为主动消费类型，push方式最终还是会从broker中pull消息。
 * 不同于pull的是，push首先要注册消费监听器，当监听器处触发后才开始消费消息，所以被称为“被动”消费。
 * @Author: Kang
 * @Date: 2019/4/28 12:07
 */
@Configuration
@Slf4j
public class RocketMqConsumerConfig {

    @Autowired
    private RocketMqConsumerTransactionListener rocketMqConsumerTransactionListener;

    /**
     * 消费者，消费
     *
     * @param rocketMqConsumerProperties
     * @return
     */
    @Bean(value = "defaultMQPushConsumer", destroyMethod = "shutdown")
    @ConditionalOnBean(RocketMqConsumerProperties.class)
    public DefaultMQPushConsumer defaultMQPushConsumer(@Autowired RocketMqConsumerProperties rocketMqConsumerProperties) {
        log.info("--------------------defaultMQPushConsumer正在创建-------------------");
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(rocketMqConsumerProperties.getDefaultGroupName());
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
