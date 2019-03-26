package com.bdxh.onecard.configration.rocketmq;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * @description: rocketmq配置类
 * @author: xuyuan
 * @create: 2019-01-15 12:26
 **/
@Data
@ConditionalOnProperty(prefix = "rocketmq.producer",name = {"defaultName","transName"})
@Component
public class RocketMqProperties {

    @Value("${rocketmq.producer.defaultName}")
    private String defaultProducerName;

    @Value("${rocketmq.producer.transName}")
    private String transProducerName;

    @Value("${spring.cloud.stream.rocketmq.binder.namesrv-addr}")
    private String namesrvAddr;

}
