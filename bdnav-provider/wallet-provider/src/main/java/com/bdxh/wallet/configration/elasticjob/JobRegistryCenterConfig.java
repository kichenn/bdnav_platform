package com.bdxh.wallet.configration.elasticjob;

import com.dangdang.ddframe.job.lite.api.listener.ElasticJobListener;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description: 任务注册中心配置
 * @author: xuyuan
 * @create: 2019-01-17 16:27
 **/
@Configuration
@EnableConfigurationProperties(JobRegistryCenterProperties.class)
@Slf4j
public class JobRegistryCenterConfig {

    @Bean(initMethod = "init",destroyMethod = "close")
    @ConditionalOnBean(JobRegistryCenterProperties.class)
    public ZookeeperRegistryCenter zookeeperRegistryCenter(@Autowired JobRegistryCenterProperties jobRegistryCenterProperties){
        log.info("任务注册中心配置开始--------------------------------------");
        ZookeeperConfiguration zookeeperConfiguration = new ZookeeperConfiguration(jobRegistryCenterProperties.getZklist(),jobRegistryCenterProperties.getNamespace());
        //连接超时时间 默认15秒
        zookeeperConfiguration.setConnectionTimeoutMilliseconds(10000);
        //会话超时时间 默认60秒
        zookeeperConfiguration.setSessionTimeoutMilliseconds(30000);
        ZookeeperRegistryCenter zookeeperRegistryCenter = new ZookeeperRegistryCenter(zookeeperConfiguration);
        return zookeeperRegistryCenter;
    }

    @Bean
    @ConditionalOnBean(JobRegistryCenterProperties.class)
    public ElasticJobListener elasticJobListener(){
        log.info("任务执行监听器配置开始--------------------------------------");
        JobLifecycleListener cusElasticJobListener = new JobLifecycleListener();
        return cusElasticJobListener;
    }

}
