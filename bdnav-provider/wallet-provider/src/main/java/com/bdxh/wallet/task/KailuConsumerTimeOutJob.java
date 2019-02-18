package com.bdxh.wallet.task;

import com.alibaba.fastjson.JSON;
import com.bdxh.common.base.constant.RocketMqConstrants;
import com.bdxh.wallet.entity.WalletKailuConsumer;
import com.bdxh.wallet.service.WalletKailuConsumerService;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: 凯路消费超时定时任务
 * @author: xuyuan
 * @create: 2019-02-18 12:11
 **/
@Component
@Slf4j
public class KailuConsumerTimeOutJob implements SimpleJob {

    @Autowired
    private WalletKailuConsumerService walletKailuConsumerService;

    @Autowired
    private DefaultMQProducer defaultMQProducer;

    @Override
    public void execute(ShardingContext shardingContext) {
        Map<String,Object> param = new HashMap<>();
        param.put("shardCount",shardingContext.getShardingTotalCount());
        param.put("shard",shardingContext.getShardingItem());
        //状态为2支付中，超过5分钟状态未更改视为超时，主动发起一卡通扣款
        param.put("timeTntervalStart",5);
        param.put("timeTntervalEnd",7);
        List<WalletKailuConsumer> walletKailuConsumers = walletKailuConsumerService.getTimeOutForTask(param);
        if (walletKailuConsumers!=null&&!walletKailuConsumers.isEmpty()){
            for (int i=0;i<walletKailuConsumers.size();i++){
                WalletKailuConsumer walletKailuConsumer = walletKailuConsumers.get(i);
                //发送消息异步处理
                String messageStr = JSON.toJSONString(walletKailuConsumer);
                Message message = new Message(RocketMqConstrants.Topic.xiancardWalletKailu,RocketMqConstrants.Tags.xiancardWalletKailu_consumer,messageStr.getBytes(Charset.forName("utf-8")));
                try {
                    defaultMQProducer.send(message);
                }catch (Exception e){
                    log.error("凯路消费消息重试发送失败：{}",messageStr);
                }
            }
        }
    }

}
