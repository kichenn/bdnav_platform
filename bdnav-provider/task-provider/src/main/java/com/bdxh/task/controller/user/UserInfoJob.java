package com.bdxh.task.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.bdxh.common.base.constant.RocketMqConstrants;
import com.bdxh.school.entity.SchoolClass;
import com.bdxh.school.entity.SchoolDept;
import com.bdxh.school.entity.SchoolUser;
import com.bdxh.school.feign.SchoolClassControllerClient;
import com.bdxh.school.feign.SchoolDeptControllerClient;
import com.bdxh.school.feign.SchoolUserControllerClient;
import com.bdxh.user.entity.BaseUser;
import com.bdxh.user.entity.Student;
import com.bdxh.user.entity.Teacher;
import com.bdxh.user.entity.TeacherDept;
import com.bdxh.user.feign.BaseUserControllerClient;
import com.bdxh.user.feign.StudentControllerClient;
import com.bdxh.user.feign.TeacherControllerClient;
import com.bdxh.user.feign.TeacherDeptControllerClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: binzh
 * @create: 2019-04-29 17:30
 **/
@Component
@Slf4j
@Configurable
@EnableScheduling
public class UserInfoJob {
    @Autowired
    private StudentControllerClient studentControllerClient;
    @Autowired
    private TeacherControllerClient teacherControllerClient;
    @Autowired
    private TeacherDeptControllerClient teacherDeptControllerClient;
    @Autowired
    private BaseUserControllerClient baseUserControllerClient;
    @Autowired
    private SchoolClassControllerClient schoolClassControllerClient;
    @Autowired
    private SchoolDeptControllerClient schoolDeptControllerClient;
    @Autowired
    private SchoolUserControllerClient schoolUserControllerClient;
    @Autowired
    private DefaultMQProducer defaultMQProducer;

    /**
     * 每天同步一次
     */
    @Scheduled(cron = "0 0 0 */1 * ?")
    public void pushMessageToMq() {
        log.info("===========每天0点推送信息至MQ给第三方同步============");
        List<Message> messageList = new ArrayList<>();
        JSONObject mesData=new JSONObject();
        //学生用户信息
        List<Student> students = studentControllerClient.findAllStudent().getResult();
        mesData.put("tableName", "t_student");
        mesData.put("data", students);
        Message studentsMsg = new Message(RocketMqConstrants.Topic.bdxhTopic, RocketMqConstrants.Tags.userInfoTag_student,System.currentTimeMillis()+"", mesData.toJSONString().getBytes());
        messageList.add(studentsMsg);
        //老师用户信息
        List<Teacher> teachers = teacherControllerClient.findAllTeacher().getResult();
        mesData.put("tableName", "t_teacher");
        mesData.put("data", teachers);
        Message teachersMsg = new Message(RocketMqConstrants.Topic.bdxhTopic, RocketMqConstrants.Tags.userInfoTag_teacher,System.currentTimeMillis()+"", mesData.toJSONString().getBytes());
        messageList.add(teachersMsg);
        //老师部门关系信息
        TeacherDept teacherDept = new TeacherDept();
        List<TeacherDept> teacherDeptList = teacherDeptControllerClient.findAllTeacherDeptInfo(teacherDept).getResult();
        mesData.put("tableName", "t_teacher_dept");
        mesData.put("data", teacherDept);
        Message teacherDeptListMsg = new Message(RocketMqConstrants.Topic.bdxhTopic, RocketMqConstrants.Tags.userInfoTag_teacherDept,System.currentTimeMillis()+"", mesData.toJSONString().getBytes());
        messageList.add(teacherDeptListMsg);
        //学校基本用户信息
        List<BaseUser> baseUserList = baseUserControllerClient.findAllBaseUserInfo().getResult();
        mesData.put("tableName", "t_base_user");
        mesData.put("data", baseUserList);
        Message baseUserListMsg = new Message(RocketMqConstrants.Topic.bdxhTopic, RocketMqConstrants.Tags.userInfoTag_baseUser,System.currentTimeMillis()+"", mesData.toJSONString().getBytes());
        messageList.add(baseUserListMsg);
        //系统用户信息
        List<SchoolUser> schoolUserList = schoolUserControllerClient.findAllSchoolUserInfo().getResult();
        mesData.put("tableName", "t_school_user");
        mesData.put("data", schoolUserList);
        Message schoolUserListMsg = new Message(RocketMqConstrants.Topic.bdxhTopic, RocketMqConstrants.Tags.schoolUserInfoTag_schoolUser,System.currentTimeMillis()+"", mesData.toJSONString().getBytes());
        messageList.add(schoolUserListMsg);
        //部门信息
        List<SchoolDept> schoolDepts = schoolDeptControllerClient.findSchoolDeptAll().getResult();
        mesData.put("tableName", "t_school_dept");
        mesData.put("data", schoolDepts);
        Message schoolDeptsMsg = new Message(RocketMqConstrants.Topic.bdxhTopic, RocketMqConstrants.Tags.schoolOrganizationTag_dept,System.currentTimeMillis()+"", mesData.toJSONString().getBytes());
        messageList.add(schoolDeptsMsg);
        //班级信息
        List<SchoolClass> schoolClasses = schoolClassControllerClient.findSchoolClassAll().getResult();
        mesData.put("tableName", "t_school_class");
        mesData.put("data", schoolClasses);
        Message schoolClassesMsg = new Message(RocketMqConstrants.Topic.bdxhTopic, RocketMqConstrants.Tags.schoolOrganizationTag_class,System.currentTimeMillis()+"", mesData.toJSONString().getBytes());
        messageList.add(schoolClassesMsg);
        try {
            defaultMQProducer.send(messageList);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("=======================推送异常=======================");
        }
            log.info("=======================推送结束=======================");
    }
}