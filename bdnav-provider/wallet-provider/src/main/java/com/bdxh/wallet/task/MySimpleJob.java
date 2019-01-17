package com.bdxh.wallet.task;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import org.springframework.stereotype.Component;

/**
 * @description: 测试任务
 * @author: xuyuan
 * @create: 2019-01-17 18:45
 **/
@Component
public class MySimpleJob implements SimpleJob {

    @Override
    public void execute(ShardingContext shardingContext) {
        System.out.println("分片参数："+shardingContext.getShardingParameter());
        System.out.println("分片数量"+shardingContext.getShardingTotalCount());
    }

}
