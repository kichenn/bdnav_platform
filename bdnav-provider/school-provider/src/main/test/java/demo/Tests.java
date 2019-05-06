package demo;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.alibaba.fastjson.JSONObject;
import com.bdxh.common.base.constant.RocketMqConstrants;
import com.bdxh.school.SchoolApplication;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.PostConstruct;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = SchoolApplication.class)
@EnableAutoConfiguration
@Slf4j
public class Tests {

    @Autowired
    private DefaultMQProducer defaultMQProducer;

    @Autowired
    private TransactionMQProducer mqProducer;


    @Test
    public void test1() throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        for (int i = 0; i < 10; i++) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("schoolClassId", "i:" + i);
            jsonObject.put("msg", "有学校院系组织架构更新了:" + i);
            Message message = new Message(RocketMqConstrants.Topic.schoolOrganizationTopic, RocketMqConstrants.Tags.schoolOrganizationTag_class, jsonObject.toJSONString().getBytes(Charset.forName("utf-8")));
            defaultMQProducer.send(message);
        }

        log.info("发送消息成功院系组织............");
    }

    @Test
    public void test2() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("schoolClassId", "111");
        jsonObject.put("msg", "有学校院系组织架构更新了");
        Message message = new Message(RocketMqConstrants.Topic.schoolOrganizationTopic, RocketMqConstrants.Tags.schoolOrganizationTag_class, jsonObject.toJSONString().getBytes(Charset.forName("utf-8")));
        try {
            mqProducer.sendMessageInTransaction(message, null);
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }

}