import com.alibaba.fastjson.JSONObject;
import com.bdxh.common.base.constant.RocketMqConstrants;
import com.bdxh.pay.PayProviderApplication;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.List;

/**
 * @Description: rocketMq测试相关方法
 * @Author: Kang
 * @Date: 2019/4/28 11:21
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PayProviderApplication.class)
@EnableAutoConfiguration
@Slf4j
public class RocketMqDemo {

//    @Autowired
//    private DefaultMQProducer defaultMQProducer;

    @Autowired
    private TransactionMQProducer transactionMQProducer;


    @Test
    public void test1() throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        //发送至mq做异步处理
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", 2);
        jsonObject.put("message", "测试2222");
        Message message = new Message(RocketMqConstrants.Topic.wechatPayWalletNotice, RocketMqConstrants.Tags.wechatPayWalletNotice_app, jsonObject.toJSONString().getBytes(Charset.forName("utf-8")));
        log.info("开始执行事务的------");
        log.info("结束执行事务的------");
        transactionMQProducer.send(message);
        log.info("发送成功");
    }

    @Test
    public void test2() {
    }
}
