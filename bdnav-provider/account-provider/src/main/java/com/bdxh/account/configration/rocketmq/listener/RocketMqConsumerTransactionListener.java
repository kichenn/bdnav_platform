package com.bdxh.account.configration.rocketmq.listener;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bdxh.account.entity.Account;
import com.bdxh.account.service.AccountService;
import com.bdxh.account.service.AccountUnqiueService;
import com.bdxh.common.base.constant.RocketMqConstrants;
import com.bdxh.common.helper.baidu.yingyan.FenceUtils;
import com.bdxh.common.helper.baidu.yingyan.constant.FenceConstant;
import com.bdxh.common.helper.baidu.yingyan.request.CreateNewEntityRequest;
import com.bdxh.common.utils.BeanMapUtils;
import com.bdxh.common.utils.HypyUtil;
import com.bdxh.common.utils.SnowflakeIdWorker;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.json.JsonObject;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

/**
 * @Description: rocketMqConsumer 消费者监听器
 * @Author: bin
 * @Date: 2019/4/29 126:16
 */
@Slf4j
@Component
public class RocketMqConsumerTransactionListener implements MessageListenerConcurrently {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountUnqiueService accountUnqiueService;
    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    /**
     * @Description: 消息监听
     * @Author: Kang
     * @Date: 2019/4/28 18:37
     */
    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
        try {
            for (MessageExt msg : msgs) {
                String topic = msg.getTopic();
                String msgBody = new String(msg.getBody(), "utf-8");
                String tags = msg.getTags();
                JSONObject json = JSONObject.parseObject(msgBody);
                log.info("收到消息:,topic:{}, tags:{},msg:{}", topic, tags, msgBody);
                //是否删除 1删除 2新增、修改
                String delFlag = json.get("delFlag").toString();
                switch (topic) {
                    case RocketMqConstrants.Topic.userOrganizationTopic:
                        switch (delFlag) {
                            case "0":
                                log.info("-------修改新增操作------");
                                //新增修改操作
                                JSONArray accountsArray = json.getJSONArray("data");
                                for (Object o : accountsArray) {
                                    JSONObject jsonObject = JSONObject.parseObject(o.toString());
                                    Account account = BeanMapUtils.map(o, Account.class);
                                    account.setUserPhone(jsonObject.getString("phone"));
                                    account.setUserName(jsonObject.getString("name"));
                                    String accountHyPy = HypyUtil.cn2py(account.getUserName());
                                    account.setLoginName(accountHyPy + account.getUserPhone());
                                    account.setPassword(new BCryptPasswordEncoder().encode("123456"));
                                    account.setUserId(jsonObject.getLong("userId"));
                                    account.setId(snowflakeIdWorker.nextId());
                                    account.setUserType(Byte.valueOf("1"));
                                    account.setLoginNameUpdate(Byte.valueOf("1"));
                                    account.setUserPhone(jsonObject.getString("phone"));
                                    log.info("开始进行入库操作");
                                    Boolean result = accountService.updateOrInsertAccount(account);
                                    log.info("修改和成功返回给我的值：  {}", result);
                                    if (result) {
                                        log.info("===========同步account成功==========");

                                    } else {
                                        log.info("===========同步account失败==========");
                                    }

                                }
                                break;
                            case "1":
                                //删除操作
                                log.info("-------删除操作------");
                                Account account = new Account();
                                JSONObject accountObject = json.getJSONObject("data");
                                account.setCardNumber(accountObject.get("cardNumber").toString());
                                account.setSchoolCode(accountObject.getString("schoolCode"));
                                Account account1 = accountService.queryAccount(account.getSchoolCode(), account.getCardNumber());
                                if (account1 != null) {
                                    accountService.delete(account1);
                                    accountUnqiueService.delAccountUnqiue(account1.getId().toString());
                                    String entityResult = FenceUtils.deleteNewEntity("accountId_" + account1.getId());
                                    JSONObject entityResultJson = JSONObject.parseObject(entityResult);
                                    if (entityResultJson.getInteger("status") != 0) {
                                        throw new RuntimeException("删除围栏中监控对象失败,状态码" + entityResultJson.getInteger("status") + "，原因:" + entityResultJson.getString("message"));
                                    }
                                }
                                break;
                        }
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
