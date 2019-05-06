package com.bdxh.user.configration.rocketmq.listener;

import com.bdxh.common.base.constant.RocketMqConstrants;
import com.bdxh.user.entity.TeacherDept;
import com.bdxh.user.mongo.FenceAlarmMongo;
import com.bdxh.user.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Case;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @Description: rocketMqConsumer 消费者监听器
 * @Author: bin
 * @Date: 2019/4/29 126:16
 */
@Slf4j
@Component
public class RocketMqConsumerTransactionListener implements MessageListenerConcurrently {
    //学生
    @Autowired
    private StudentService studentService;


    //老师部门关系
    @Autowired
    private TeacherDeptService teacherDeptService;

    //浏览器访问日志信息
    @Autowired
    private VisitLogsService visitLogsService;

    //老师
    @Autowired
    private TeacherService teacherService;

    //家长
    @Autowired
    private FamilyService familyService;

    //基础用户信息
    @Autowired
    private BaseUserService baseUserService;

    //家长围栏
    @Autowired
    private FamilyFenceService familyFenceService;

    //围栏日志
    @Autowired
    private FenceAlarmService fenceAlarmService;

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
                switch (topic) {
                    case RocketMqConstrants.Topic.schoolOrganizationTopic:
                        {
                            switch (tags){
                                case RocketMqConstrants.Tags.schoolOrganizationTag_dept:{
                                    TeacherDept teacherDept=new TeacherDept();
                                    teacherDept.setOperatorName("");
                                    teacherDeptService.update(teacherDept);
                                }
                                case RocketMqConstrants.Tags.schoolOrganizationTag_class:{

                                }
                                case RocketMqConstrants.Tags.schoolOrganizationTag_school:{

                                }
                            }
                    }
                }
                log.info("studentService:{}",studentService);

                log.info("收到消息:,topic:{}, tags:{},msg:{}", topic, tags, msgBody);
                msg.getTags();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
