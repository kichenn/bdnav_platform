package com.bdxh.user.configration.rocketmq.listener;

import com.alibaba.fastjson.JSONObject;
import com.bdxh.common.base.constant.RocketMqConstrants;
import com.bdxh.common.utils.BeanMapUtils;
import com.bdxh.user.entity.TeacherDept;
import com.bdxh.user.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
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
            log.info("进来了");
            for (MessageExt msg : msgs) {
                String topic = msg.getTopic();
                String msgBody = new String(msg.getBody(), "utf-8");
                String tags = msg.getTags();
                JSONObject deptJson=JSONObject.parseObject(msgBody);
                Map<String, String> map =(Map<String, String>) deptJson.get("data");
                JSONObject data=JSONObject.parseObject(map.toString());
                switch (topic) {
                    case RocketMqConstrants.Topic.schoolOrganizationTopic:
                        {
                            switch (tags){
                                case RocketMqConstrants.Tags.schoolOrganizationTag_dept:{
                                    //是否是父节点
                                    String type = data.getString("parentId").equals("-1")?"0":"1";
                                    //部门路径名称
                                    String deptNames = data.getString("thisUrl");
                                    //部门名称
                                    String deptName = data.getString("name");
                                    //学校schoolCode
                                    String schoolCode = data.getString("schoolCode");
                                    //部门ID
                                    String deptId=data.getString("id");
                                    List<TeacherDept> teacherDeptList=teacherDeptService.findTeacherDeptsBySchoolCode(schoolCode,deptId,type);
                                   List<TeacherDept> newTeacherDeptList=new ArrayList<>();
                                    for (TeacherDept teacherDept : teacherDeptList) {
                                        //如果修改的部门名称是当前所在部门的子部门还需要修改deptName列的数据
                                        if(teacherDept.getDeptId().equals(Long.parseLong(deptId))){
                                            teacherDept.setDeptName(deptName);
                                        }
                                        //如果不是只需要修改deptNames列
                                        teacherDept.setDeptNames(deptNames);
                                        newTeacherDeptList.add(teacherDept);
                                    }
                                    teacherDeptService.batchUpdateTeacherDept(newTeacherDeptList);
                                    log.info("我修改了部门");
                                }
                                case RocketMqConstrants.Tags.schoolOrganizationTag_class:{

                                    log.info("我修改了班级");
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
