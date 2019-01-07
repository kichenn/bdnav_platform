package com.bdxh.wallet.configration.idgenerator;

import com.bdxh.common.utils.SnowflakeIdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description:
 * @author: xuyuan
 * @create: 2018-12-30 20:40
 **/
@Configuration
@EnableConfigurationProperties(IdGeneratorProperties.class)
public class IdGeneratorConfigration {

    @Bean
    @ConditionalOnBean(IdGeneratorProperties.class)
    public SnowflakeIdWorker snowflakeIdWorker(@Autowired IdGeneratorProperties idGeneratorProperties){
        SnowflakeIdWorker snowflakeIdWorker=new SnowflakeIdWorker(idGeneratorProperties.getWorkerId(),idGeneratorProperties.getDatacenterId());
        return snowflakeIdWorker;
    }

}
