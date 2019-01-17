package com.bdxh.wallet.configration.elasticjob;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @description: 任务注册中心属性
 * @author: xuyuan
 * @create: 2019-01-17 16:29
 **/
@Data
@ConfigurationProperties(prefix = "elastic-job.registry")
@ConditionalOnProperty(prefix = "elastic-job.registry",name = {"zklist","namespace"})
@Configuration
public class JobRegistryCenterProperties {

    private String zklist;

    private String namespace;
}
