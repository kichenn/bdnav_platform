package com.bdxh.wallet.configration.idgenerator;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
* @Description:
* @Author: Kang
* @Date: 2019/7/10 17:43
*/
@Data
@ConfigurationProperties(prefix = "id-generator")
@ConditionalOnProperty(prefix = "id-generator",name = {"workerId","datacenterId"})
@Configuration
public class IdGeneratorProperties {

    private long workerId;

    private long datacenterId;
}
