package com.bdxh.user.configration.rocketmq.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * @Description: rocketmq生产者配置类
 * @Author: bin
 * @Date: 2019/4/29 126:16
 */
@Data
@ConditionalOnProperty(prefix = "rocketmq.producer", name = {"defaultName", "transName"})
@Component
public class RocketMqProducerProperties {

    @Value("${rocketmq.producer.defaultName}")
    private String defaultProducerName;

    @Value("${rocketmq.producer.transName}")
    private String transProducerName;

    @Value("${spring.cloud.stream.rocketmq.binder.namesrv-addr}")
    private String namesrvAddr;

}
