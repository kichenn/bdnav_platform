package com.bdxh.wallet.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bdxh.common.base.constant.RedisClusterConstrants;
import com.bdxh.common.base.constant.RocketMqConstrants;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.onecard.feign.XianCardControllerClient;
import com.bdxh.wallet.entity.WalletAccountRecharge;
import com.bdxh.wallet.service.WalletAccountRechargeService;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @description: 一卡通充值超时任务类
 * @author: xuyuan
 * @create: 2019-01-18 15:31
 **/
@Component
@Slf4j
public class WalletRechargeDataflowJob implements DataflowJob<WalletAccountRecharge> {

    @Autowired
    private WalletAccountRechargeService walletAccountRechargeService;

    @Autowired
    private XianCardControllerClient xianCardControllerClient;

    @Autowired
    private DefaultMQProducer defaultMQProducer;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Override
    public List<WalletAccountRecharge> fetchData(ShardingContext shardingContext) {
        Map<String,Object> param = new HashMap<>();
        param.put("shardCount",shardingContext.getShardingTotalCount());
        param.put("shard",shardingContext.getShardingItem());
        //状态为8充值成功，订单时间超过6分钟没有得到一卡通返回结果视为超时，主动发起一卡通扣款
        param.put("timeTnterval",6);
        List<WalletAccountRecharge> walletAccountRecharges = walletAccountRechargeService.querySerailNoNullForTask(param);
        return walletAccountRecharges;
    }

    @Override
    public void processData(ShardingContext shardingContext, List<WalletAccountRecharge> list) {
        if (list!=null&&list.size()>0){
            for (int i=0;i<list.size();i++){
                WalletAccountRecharge walletAccountRecharge = list.get(i);
                //查询充值记录
                String orderNo = String.valueOf(walletAccountRecharge.getOrderNo());
                Wrapper wrapper = xianCardControllerClient.queryAddResult(walletAccountRecharge.getSchoolCode(), orderNo);
                if (wrapper.getCode()==200){
                    String queryResultStr=(String) wrapper.getResult();
                    JSONObject queryResult = JSON.parseObject(queryResultStr);
                    String code = queryResult.getString("code");
                    //未找到充值记录，直接发起充值 ps返回0000000代表充值成功，我们可以更新流水号，但是对方并没有返回，故不做处理
                    if (StringUtils.equals(code,"100009")){
                        //做幂等性处理
                        String notice = (String)redisTemplate.opsForValue().get(RedisClusterConstrants.KeyPrefix.wechatpay_wallet_query_xiancard_result+orderNo);
                        if (!StringUtils.equals(notice,"1")){
                            //发送消息异步处理
                            String messageStr = JSON.toJSONString(walletAccountRecharge);
                            Message message = new Message(RocketMqConstrants.Topic.xiancardWalletRecharge,RocketMqConstrants.Tags.xiancardWalletRecharge_add,messageStr.getBytes(Charset.forName("utf-8")));
                            try {
                                defaultMQProducer.send(message);
                                redisTemplate.opsForValue().set(RedisClusterConstrants.KeyPrefix.wechatpay_wallet_query_xiancard_result+orderNo,"1",5, TimeUnit.MINUTES);
                            }catch (Exception e){
                                log.error("钱包服务充值一卡通消息发送失败："+orderNo);
                                throw new RuntimeException(e.getMessage());
                            }
                        }
                    }
                }
            }
        }
    }

}
