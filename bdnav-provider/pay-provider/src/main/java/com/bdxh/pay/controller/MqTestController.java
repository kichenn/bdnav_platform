package com.bdxh.pay.controller;

import com.alibaba.fastjson.JSONObject;
import com.bdxh.common.base.constant.RocketMqConstrants;
import com.bdxh.pay.configration.rocketmq.listener.RocketMqProducerTransactionListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.Charset;

/**
 * @Description: mq测试控制器
 * @Author: Kang
 * @Date: 2019/4/28 15:51
 */
@RestController
@RequestMapping("/MqTestController")
@Slf4j
@Validated
public class MqTestController {

    @Autowired
    private TransactionMQProducer transactionMQProducer;


    /**
     * 发送消息
     *
     * @throws InterruptedException
     * @throws RemotingException
     * @throws MQClientException
     * @throws MQBrokerException
     */
    @GetMapping("/test1")
    @Transactional(rollbackFor = Exception.class)
    public void test1() throws MQClientException {
        //发送至mq做异步处理
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", 001);
        jsonObject.put("message", "测试001");
        Message message = new Message(RocketMqConstrants.Topic.wechatPayWalletNotice, RocketMqConstrants.Tags.wechatPayWalletNotice_app, jsonObject.toJSONString().getBytes(Charset.forName("utf-8")));
        log.info("开始执行事务的------");
        transactionMQProducer.sendMessageInTransaction(message, null);
        log.info("结束执行事务的------");
//        transactionMQProducer.send(message);
        log.info("发送成功");
    }

    /**
     * @Description: 测试发送（模拟微信支付成功后的mq消息操作）
     * @Author: Kang
     * @Date: 2019/6/21 12:02
     */
    @GetMapping("/test2")
    public void test2() throws MQClientException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("orderNo", "591720830082482176");
        jsonObject.put("thirdOrderNo", "4200000306201906210338906399");
        jsonObject.put("resultCode", "SUCCESS");
        Message message = new Message(RocketMqConstrants.Topic.wechatPayWalletNotice, RocketMqConstrants.Tags.wechatPayWalletNotice_js, jsonObject.toJSONString().getBytes(Charset.forName("utf-8")));
        log.info("开始执行事务的------");
        transactionMQProducer.sendMessageInTransaction(message, null);
        log.info("结束执行事务的------");
    }
}
