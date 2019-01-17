package com.bdxh.wallet.configration.elasticjob;

import com.bdxh.wallet.task.MySimpleJob;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.api.listener.ElasticJobListener;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * @description: 任务配置类
 * @author: xuyuan
 * @create: 2019-01-17 17:31
 **/
@Configuration
@Slf4j
public class ElasticJobConfig {

    @Autowired
    private Environment environment;

    @Autowired(required = false)
    private ZookeeperRegistryCenter zookeeperRegistryCenter;

    @Autowired(required = false)
    private ElasticJobListener elasticJobListener;

    private LiteJobConfiguration getLiteJobConfiguration(Class<? extends SimpleJob> jobClass, String cron, int shardingTotalCount,
                                                         String shardingItemParameters) {
        return LiteJobConfiguration.newBuilder(new SimpleJobConfiguration(JobCoreConfiguration.newBuilder(environment.getProperty("spring.application.name")+":"+jobClass.getName(), cron, shardingTotalCount)
                .shardingItemParameters(shardingItemParameters).build(), jobClass.getCanonicalName())).overwrite(true).build();
    }

    @Bean(initMethod = "init")
    @ConditionalOnBean(MySimpleJob.class)
    public JobScheduler simpleJobScheduler(@Autowired MySimpleJob mySimpleJob) {
        return new SpringJobScheduler(mySimpleJob, zookeeperRegistryCenter,
                getLiteJobConfiguration(mySimpleJob.getClass(), "0/5 * * * * ?", 2, "0=A,1=B,2=C"),
                elasticJobListener);
    }

}
