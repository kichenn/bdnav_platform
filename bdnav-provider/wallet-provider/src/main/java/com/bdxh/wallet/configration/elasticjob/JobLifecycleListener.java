package com.bdxh.wallet.configration.elasticjob;

import com.bdxh.common.utils.ObjectUtil;
import com.dangdang.ddframe.job.executor.ShardingContexts;
import com.dangdang.ddframe.job.lite.api.listener.ElasticJobListener;
import lombok.extern.slf4j.Slf4j;

/**
 * @description: 任务执行监听类
 * @author: xuyuan
 * @create: 2019-01-17 17:00
 **/
@Slf4j
public class JobLifecycleListener implements ElasticJobListener {

    private ThreadLocal<Long> start = new InheritableThreadLocal<>();

    @Override
    public void beforeJobExecuted(ShardingContexts shardingContexts) {
        long startTime = System.currentTimeMillis();
        start.set(startTime);
        log.info("===>{} JOB BEGIN TIME: {} <===",shardingContexts.getJobName(), ObjectUtil.getNowStr());
    }

    @Override
    public void afterJobExecuted(ShardingContexts shardingContexts) {
        long cast = System.currentTimeMillis()-start.get();
        start.remove();
        log.info("===>{} JOB END TIME: {},TOTAL CAST: {}ms <===",shardingContexts.getJobName(), ObjectUtil.getNowStr(), cast);
    }

}
