package com.bdxh.wallet.configration.elasticjob;

import com.bdxh.wallet.task.WalletClearSimpleJob;
import com.bdxh.wallet.task.WalletRechargeDataflowJob;
import com.bdxh.wallet.task.WechatNoticeDataflowJob;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.dataflow.DataflowJobConfiguration;
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

    /**
     * 简单任务配置
     * @param jobClass job执行类 继承SimpleJob
     * @param cron cron表达式
     * @param shardingTotalCount 分片数量
     * @param shardingItemParameters 分片参数
     * @param failover 故障转移 开启之后数据可能重复抓取，需做幂等性处理
     * @return
     */
    private LiteJobConfiguration getSimpleLiteJobConfiguration(Class<? extends SimpleJob> jobClass, String cron, int shardingTotalCount,
                                                               String shardingItemParameters, boolean failover) {
        JobCoreConfiguration jobCoreConfiguration = JobCoreConfiguration.newBuilder(environment.getProperty("spring.application.name") + ":" + jobClass.getName(), cron, shardingTotalCount)
                .shardingItemParameters(shardingItemParameters).failover(failover).build();
        SimpleJobConfiguration simpleJobConfiguration = new SimpleJobConfiguration(jobCoreConfiguration, jobClass.getCanonicalName());
        LiteJobConfiguration liteJobConfiguration = LiteJobConfiguration.newBuilder(simpleJobConfiguration).overwrite(true).build();
        return liteJobConfiguration;
    }

    /**
     * 流式任务配置
     * @param jobClass job执行类 继承DataflowJob
     * @param cron cron表达式
     * @param shardingTotalCount 分片数量
     * @param shardingItemParameters 分片参数
     * @param failover 故障转移 开启之后数据可能重复抓取，需做幂等性处理
     * @param streamingProcess 流式处理 开启之后抓取数据不为空则会一直运行下去，建议处理后修改状态，避免任务永久执行下去
     * @return
     */
    private LiteJobConfiguration getDataflowLiteJobConfiguration(Class<? extends DataflowJob> jobClass, String cron, int shardingTotalCount,
                                                                 String shardingItemParameters, boolean failover, boolean streamingProcess) {
        JobCoreConfiguration jobCoreConfiguration = JobCoreConfiguration.newBuilder(environment.getProperty("spring.application.name") + ":" + jobClass.getName(), cron, shardingTotalCount)
                .shardingItemParameters(shardingItemParameters).failover(failover).build();
        DataflowJobConfiguration dataflowJobConfiguration = new DataflowJobConfiguration(jobCoreConfiguration, jobClass.getCanonicalName(),streamingProcess);
        LiteJobConfiguration liteJobConfiguration = LiteJobConfiguration.newBuilder(dataflowJobConfiguration).overwrite(true).build();
        return liteJobConfiguration;
    }

    /**
     * 钱包充值超时订单清理定时任务
     * @param walletClearSimpleJob
     * @return
     */
    @Bean(initMethod = "init")
    @ConditionalOnBean(WalletClearSimpleJob.class)
    public JobScheduler walletClearScheduler(@Autowired WalletClearSimpleJob walletClearSimpleJob) {
        LiteJobConfiguration simpleLiteJobConfiguration = getSimpleLiteJobConfiguration(walletClearSimpleJob.getClass(), "0/15 * * * * ?", 1, "", false);
        SpringJobScheduler simpleJobScheduler = new SpringJobScheduler(walletClearSimpleJob, zookeeperRegistryCenter, simpleLiteJobConfiguration, elasticJobListener);
        return simpleJobScheduler;
    }

    /**
     * 钱包充值微信通知超时定时任务
     * @param wechatNoticeDataflowJob
     * @return
     */
    @Bean(initMethod = "init")
    @ConditionalOnBean(WechatNoticeDataflowJob.class)
    public JobScheduler wechatNoticeScheduler(@Autowired WechatNoticeDataflowJob wechatNoticeDataflowJob) {
        LiteJobConfiguration dataflowJobConfiguration = getDataflowLiteJobConfiguration(wechatNoticeDataflowJob.getClass(), "0/10 * * * * ?", 9, "", false, false);
        SpringJobScheduler dataflowJobScheduler = new SpringJobScheduler(wechatNoticeDataflowJob, zookeeperRegistryCenter, dataflowJobConfiguration, elasticJobListener);
        return dataflowJobScheduler;
    }

    /**
     * 钱包一卡通钱包充值超时定时任务
     * @param walletRechargeDataflowJob
     * @return
     */
    @Bean(initMethod = "init")
    @ConditionalOnBean(WalletRechargeDataflowJob.class)
    public JobScheduler xianCardRechargeScheduler(@Autowired WalletRechargeDataflowJob walletRechargeDataflowJob) {
        LiteJobConfiguration dataflowJobConfiguration = getDataflowLiteJobConfiguration(walletRechargeDataflowJob.getClass(), "0/10 * * * * ?", 9, "", false, false);
        SpringJobScheduler dataflowJobScheduler = new SpringJobScheduler(walletRechargeDataflowJob, zookeeperRegistryCenter, dataflowJobConfiguration, elasticJobListener);
        return dataflowJobScheduler;
    }

}
