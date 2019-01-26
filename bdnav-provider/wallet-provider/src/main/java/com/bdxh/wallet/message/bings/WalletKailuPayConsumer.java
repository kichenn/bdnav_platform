package com.bdxh.wallet.message.bings;

import com.alibaba.fastjson.JSON;
import com.bdxh.common.base.constant.RedisClusterConstrants;
import com.bdxh.common.base.constant.RocketMqConstrants;
import com.bdxh.common.base.enums.PayStatusEnum;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.onecard.dto.XianSubBlanceDto;
import com.bdxh.onecard.feign.XianCardControllerClient;
import com.bdxh.wallet.entity.WalletKailuConsumer;
import com.bdxh.wallet.message.stream.WalletKailuPaySink;
import com.bdxh.wallet.service.WalletKailuConsumerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.Message;

import java.nio.charset.Charset;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @description: 凯路消息消费者
 * @author: xuyuan
 * @create: 2019-01-25 10:54
 **/
@EnableBinding(WalletKailuPaySink.class)
@Slf4j
public class WalletKailuPayConsumer {

    @Autowired
    private WalletKailuConsumerService walletKailuConsumerService;

    @Autowired
    private XianCardControllerClient xianCardControllerClient;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Autowired
    private DefaultMQProducer defaultMQProducer;

    @StreamListener(WalletKailuPaySink.INPUT)
    public void reciveWalletKailu(Message<String> message){
        String consumer = message.getPayload();
        log.info("收到凯路消费消息："+consumer);
        WalletKailuConsumer walletKailuConsumer = JSON.parseObject(consumer, WalletKailuConsumer.class);
        XianSubBlanceDto xianSubBlanceDto = new XianSubBlanceDto();
        xianSubBlanceDto.setSchoolCode(walletKailuConsumer.getSchoolCode());
        xianSubBlanceDto.setCardNumber(walletKailuConsumer.getCardNumber());
        xianSubBlanceDto.setMoney(walletKailuConsumer.getPayMoney());
        xianSubBlanceDto.setOrderNo(String.valueOf(walletKailuConsumer.getOrderNo()));
        xianSubBlanceDto.setUserName(walletKailuConsumer.getName());
        Wrapper wrapper = xianCardControllerClient.subBalance(xianSubBlanceDto);
        log.info("订单号{}凯路一卡通消费返回结果：{}",walletKailuConsumer.getOrderNo(),JSON.toJSON(wrapper));
        //发起扣款失败 更新状态等待下次重试
        if (wrapper.getCode()!=200){
            String value = (String)redisTemplate.opsForValue().get(RedisClusterConstrants.KeyPrefix.kailu_wallet_sub_xiancard_fail_update + walletKailuConsumer.getOutOrderNo());
            if (!StringUtils.equals(value,"1")){
                walletKailuConsumer.setPayStatus(PayStatusEnum.PAY_FAIL.getCode());
                walletKailuConsumer.setUpdateDate(new Date());
                walletKailuConsumer.setMessage((String) wrapper.getResult());
                walletKailuConsumerService.update(walletKailuConsumer);
                redisTemplate.opsForValue().set(RedisClusterConstrants.KeyPrefix.kailu_wallet_sub_xiancard_fail_update+walletKailuConsumer.getOutOrderNo(),"1",2, TimeUnit.HOURS);
            }
            throw new RuntimeException("一卡通消费失败");
        }
        //发送消息更新订单状态
        walletKailuConsumer.setAcceptseq((String) wrapper.getResult());
        walletKailuConsumer.setPayStatus(PayStatusEnum.PAY_SUCCESS.getCode());
        walletKailuConsumer.setUpdateDate(new Date());
        walletKailuConsumer.setMessage("支付成功");
        String messageStr=JSON.toJSONString(walletKailuConsumer);
        org.apache.rocketmq.common.message.Message rocketmqMessage = new org.apache.rocketmq.common.message.Message(RocketMqConstrants.Topic.xiancardWalletKailuUpdate,RocketMqConstrants.Tags.xiancardWalletKailuUpdate_update,messageStr.getBytes(Charset.forName("utf-8")));
        try {
            defaultMQProducer.send(rocketmqMessage);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(),e.getStackTrace());
            log.error("凯路消费订单更新状态消息发送失败，订单号{}",walletKailuConsumer.getOrderNo());
            throw new RuntimeException("凯路消费订单更新状态消息发送失败");
        }
    }

}
