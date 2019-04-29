package com.bdxh.pay.configration.rocketmq.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;


/**
 * @Description: rocketmq 消费者配置类
 * @Author: Kang
 * @Date: 2019/4/28 12:15
 */
@Data
@ConditionalOnProperty(prefix = "rocketmq.consumer", name = {"groupName", "topic", "tag"})
@Component
public class RocketMqConsumerProperties {

    @Value("${rocketmq.consumer.groupName}")
    private String defaultGroupName;

    @Value("${rocketmq.consumer.topic}")
    private String topic;

    @Value("${rocketmq.consumer.tag}")
    private String tag;

    @Value("${spring.cloud.stream.rocketmq.binder.namesrv-addr}")
    private String namesrvAddr;

}
