package com.bdxh.onecard.configration.rocketmq;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.TransactionCheckListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description: rocketmq配置类
 * @author: xuyuan
 * @create: 2019-01-15 12:00
 **/
@Configuration
@Slf4j
public class RocketMqConfigration {

    @Bean(value = "defaultMQProducer",destroyMethod = "shutdown")
    @ConditionalOnBean(RocketMqProperties.class)
    public DefaultMQProducer defaultMQProducer(@Autowired RocketMqProperties rocketMqProperties) throws MQClientException {
        log.info("defaultProducer 正在创建---------------------------------------");
        DefaultMQProducer producer = new DefaultMQProducer(rocketMqProperties.getDefaultProducerName());
        producer.setNamesrvAddr(rocketMqProperties.getNamesrvAddr());
        producer.setVipChannelEnabled(true);
        producer.setSendMsgTimeout(5000);
        producer.setMaxMessageSize(4*1024);
        producer.setRetryTimesWhenSendFailed(2);
        producer.setRetryTimesWhenSendAsyncFailed(5);
        producer.start();
        log.info("defaultProducer server开启成功---------------------------------.");
        return producer;
    }

    @Bean(value = "transactionMQProducer ",destroyMethod = "shutdown")
    @ConditionalOnBean(RocketMqProperties.class)
    @ConditionalOnProperty(name = "rocketmq.producer.transEnable" ,havingValue = "true")
    public TransactionMQProducer transactionMQProducer (@Autowired RocketMqProperties rocketMqProperties, @Autowired TransactionCheckListener transactionCheckListener) throws MQClientException {
        log.info("transactionMQProducer 正在创建---------------------------------------");
        TransactionMQProducer producer = new TransactionMQProducer(rocketMqProperties.getTransProducerName());
        producer.setNamesrvAddr(rocketMqProperties.getNamesrvAddr());
        producer.setTransactionCheckListener(transactionCheckListener);
        producer.setCheckThreadPoolMinSize(5);
        producer.setCheckThreadPoolMaxSize(20);
        producer.setCheckRequestHoldMax(2000);
        producer.setVipChannelEnabled(true);
        producer.setSendMsgTimeout(10000);
        producer.setMaxMessageSize(4*1024);
        producer.setRetryTimesWhenSendFailed(2);
        producer.setRetryTimesWhenSendAsyncFailed(5);
        producer.start();
        log.info("transactionMQProducer server开启成功---------------------------------.");
        return producer;
    }

}
