package com.bdxh.wallet.task;

import com.alibaba.fastjson.JSONObject;
import com.bdxh.common.base.constant.RedisClusterConstrants;
import com.bdxh.common.base.constant.RocketMqConstrants;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.pay.feign.WechatCommonControllerClient;
import com.bdxh.pay.vo.WechatOrderQueryVo;
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
 * @description: 微信支付回调超时任务类
 * @author: xuyuan
 * @create: 2019-01-18 14:07
 **/
@Component
@Slf4j
public class WechatNoticeDataflowJob implements DataflowJob<WalletAccountRecharge> {

    @Autowired
    private WalletAccountRechargeService walletAccountRechargeService;

    @Autowired
    private WechatCommonControllerClient wechatCommonControllerClient;

    @Autowired
    private DefaultMQProducer defaultMQProducer;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Override
    public List<WalletAccountRecharge> fetchData(ShardingContext shardingContext) {
        Map<String,Object> param = new HashMap<>();
        param.put("shardCount",shardingContext.getShardingTotalCount());
        param.put("shard",shardingContext.getShardingItem());
        //状态为2支付中，超过5分钟状态未更改视为超时，主动发起查询根据结果进行一卡通扣款
        param.put("timeTntervalStart",5);
        param.put("timeTntervalEnd",7);
        List<WalletAccountRecharge> walletAccountRecharges = walletAccountRechargeService.queryPayingDataForTask(param);
        return walletAccountRecharges;
    }

    @Override
    public void processData(ShardingContext shardingContext, List<WalletAccountRecharge> list) {
        if (list!=null&&list.size()>0){
            for (int i=0;i<list.size();i++){
                WalletAccountRecharge walletAccountRecharge = list.get(i);
                String orderNo = String.valueOf(walletAccountRecharge.getOrderNo());
                Wrapper wrapper = wechatCommonControllerClient.wechatAppPayOrderQuery(orderNo);
                if (wrapper.getCode()==200){
                    WechatOrderQueryVo wechatOrderQueryVo = (WechatOrderQueryVo)wrapper.getResult();
                    String payResult = wechatOrderQueryVo.getPayResult();
                    log.info("钱包充值订单号{}查询结果：{}",orderNo,payResult);
                    // 查询结果 支付成功发送消息至mq
                    if (StringUtils.equals(payResult,"SUCCESS")){
                        //做幂等性处理
                        String notice = (String)redisTemplate.opsForValue().get(RedisClusterConstrants.KeyPrefix.wechatpay_wallet_query_wechart_result + orderNo);
                        if (!StringUtils.equals(notice,"1")){
                            //发送至mq做异步处理
                            JSONObject jsonObject=new JSONObject();
                            jsonObject.put("orderNo",orderNo);
                            jsonObject.put("resultCode",payResult);
                            jsonObject.put("thirdOrderNo",wechatOrderQueryVo.getThirdOrderNo());
                            Message message = new Message(RocketMqConstrants.Topic.wechatPayWalletNotice,RocketMqConstrants.Tags.wechatPayWalletNotice_query,jsonObject.toJSONString().getBytes(Charset.forName("utf-8")));
                            try {
                                defaultMQProducer.send(message);
                                redisTemplate.opsForValue().set(RedisClusterConstrants.KeyPrefix.wechatpay_wallet_query_wechart_result+orderNo,"1",2, TimeUnit.MINUTES);
                            } catch (Exception e) {
                                e.printStackTrace();
                                log.error("钱包充值订单查询任务：订单号{}发送消息失败",orderNo,e.getStackTrace());
                            }
                        }
                    }
                }
            }
        }
    }
}
