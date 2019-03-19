package com.bdxh.wallet.service.impl;

import com.alibaba.fastjson.JSON;
import com.bdxh.common.base.constant.RocketMqConstrants;
import com.bdxh.common.base.enums.BusinessStatusEnum;
import com.bdxh.common.base.enums.PayStatusEnum;
import com.bdxh.common.utils.BeanMapUtils;
import com.bdxh.common.utils.SnowflakeIdWorker;
import com.bdxh.common.support.BaseService;
import com.bdxh.wallet.dto.WalletKailuOrderDto;
import com.bdxh.wallet.entity.WalletKailuConsumer;
import com.bdxh.wallet.persistence.WalletKailuConsumerMapper;
import com.bdxh.wallet.service.WalletKailuConsumerService;
import com.bdxh.wallet.vo.WalletKailuOrderVo;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

/**
 * @description: 凯路消费service实现类
 * @author: xuyuan
 * @create: 2019-01-25 16:25
 **/
@Service
@Slf4j
public class WalletKailuConsumerServiceImpl extends BaseService<WalletKailuConsumer> implements WalletKailuConsumerService {

    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    @Autowired
    private WalletKailuConsumerMapper walletKailuConsumerMapper;

    @Autowired
    private DefaultMQProducer defaultMQProducer;


    @Transactional(rollbackFor = Exception.class)
    @Override
    public WalletKailuOrderVo kailuOrder(WalletKailuOrderDto walletKailuOrderDto) {
        //查询订单
        WalletKailuConsumer walletKailuConsumer = new WalletKailuConsumer();
        walletKailuConsumer.setOutOrderNo(walletKailuOrderDto.getOutOrderNo());
        walletKailuConsumer = walletKailuConsumerMapper.selectOne(walletKailuConsumer);
        Preconditions.checkArgument(walletKailuConsumer==null,"订单号重复");
        //插入消费记录
        walletKailuConsumer = BeanMapUtils.map(walletKailuOrderDto, WalletKailuConsumer.class);
        long orderNo = snowflakeIdWorker.nextId();
        walletKailuConsumer.setOrderNo(orderNo);
        //订单状态 支付中
        walletKailuConsumer.setPayStatus(PayStatusEnum.PAYING.getCode());
        walletKailuConsumer.setBusinessStatus(BusinessStatusEnum.NO_PROCESS.getCode());
        walletKailuConsumerMapper.insertSelective(walletKailuConsumer);
        //发送消息异步处理
        String messageStr = JSON.toJSONString(walletKailuConsumer);
        Message message = new Message(RocketMqConstrants.Topic.xiancardWalletKailu,RocketMqConstrants.Tags.xiancardWalletKailu_consumer,messageStr.getBytes(Charset.forName("utf-8")));
        try {
            defaultMQProducer.send(message);
        }catch (Exception e){
            log.error("凯路消费消息发送失败：{}",messageStr);
            throw new RuntimeException(e.getMessage());
        }
        WalletKailuOrderVo walletKailuOrderVo = new WalletKailuOrderVo();
        walletKailuOrderVo.setOrderNo(orderNo);
        walletKailuOrderVo.setOutOrderNo(walletKailuOrderDto.getOutOrderNo());
        return walletKailuOrderVo;
    }

    @Override
    public List<WalletKailuConsumer> getFailForTask(Map<String, Object> param) {
        List<WalletKailuConsumer> walletKailuConsumers = walletKailuConsumerMapper.getFailForTask(param);
        return walletKailuConsumers;
    }

    @Override
    public List<WalletKailuConsumer> getTimeOutForTask(Map<String, Object> param) {
        List<WalletKailuConsumer> walletKailuConsumers = walletKailuConsumerMapper.getTimeOutForTask(param);
        return walletKailuConsumers;
    }

}
