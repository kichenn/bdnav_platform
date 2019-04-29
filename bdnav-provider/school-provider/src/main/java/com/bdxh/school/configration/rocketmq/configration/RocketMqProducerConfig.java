package com.bdxh.school.configration.rocketmq.configration;

import com.bdxh.school.configration.rocketmq.listener.RocketMqProducerTransactionListener;
import com.bdxh.school.configration.rocketmq.properties.RocketMqProducerProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Description: rocketMQ 生产者配置类
 *  rocketMQ消息发送是
 * @Author: Kang
 * @Date: 2019/4/28 12:06
 */
@Configuration
@Slf4j
public class RocketMqProducerConfig {

    @Autowired
    private RocketMqProducerTransactionListener rocketMqTransactionListener;

    @Bean(value = "defaultMQProducer", destroyMethod = "shutdown")
    @ConditionalOnBean(RocketMqProducerProperties.class)
    public DefaultMQProducer defaultMQProducer(@Autowired RocketMqProducerProperties rocketMqProperties) throws MQClientException {
        log.info("--------------------defaultProducer 正在创建--------------------");
        DefaultMQProducer producer = new DefaultMQProducer(rocketMqProperties.getDefaultProducerName());
        producer.setNamesrvAddr(rocketMqProperties.getNamesrvAddr());
        producer.setVipChannelEnabled(true);
        producer.setSendMsgTimeout(5000);
        //队列接收最大的消息size
        producer.setMaxMessageSize(4 * 1024);
        //发送消息失败，重试次数
        producer.setRetryTimesWhenSendFailed(5);
        //slave节点,将master主节点同步到slave失败，重试次数
        producer.setRetryTimesWhenSendAsyncFailed(5);
        producer.start();
        log.info("--------------------defaultProducer server开启成功--------------------");
        return producer;
    }

    @Bean(value = "transactionMQProducer ", destroyMethod = "shutdown")
    @ConditionalOnBean(RocketMqProducerProperties.class)
    @ConditionalOnProperty(name = "rocketmq.producer.transEnable", havingValue = "true")
    public TransactionMQProducer transactionMQProducer(@Autowired RocketMqProducerProperties rocketMqProperties) throws MQClientException {
        log.info("--------------------transactionMQProducer 正在创建-----------------------------");
        TransactionMQProducer producer = new TransactionMQProducer(rocketMqProperties.getTransProducerName());
        producer.setNamesrvAddr(rocketMqProperties.getNamesrvAddr());
        ExecutorService executorService = new ThreadPoolExecutor(2, 8, 90, TimeUnit.SECONDS, new LinkedBlockingDeque<>());
        producer.setExecutorService(executorService);
        producer.setTransactionListener(rocketMqTransactionListener);
        producer.setVipChannelEnabled(true);
        producer.setSendMsgTimeout(10000);
        //队列接收最大的消息size
        producer.setMaxMessageSize(4 * 1024);
        //发送消息失败，重试次数
        producer.setRetryTimesWhenSendFailed(5);
        //slave节点,将master主节点同步到slave失败，重试次数
        producer.setRetryTimesWhenSendAsyncFailed(5);
        producer.start();
        log.info("--------------------transactionMQProducer server开启成功--------------------");
        return producer;
    }

}
