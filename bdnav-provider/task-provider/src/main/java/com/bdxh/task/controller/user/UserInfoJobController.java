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
public class UserInfoJobController {
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
    @Scheduled(cron = "0 0 0 * * ?")
    public void pushMessageToMq() {
      /*  synchronized (this) {
            log.info("===========每天0点推送信息至MQ给第三方同步============");
            JSONObject mesData = new JSONObject();
            //学生用户信息

            List<Student> students = studentControllerClient.findAllStudent().getResult();
            if (students.size() != 0) {
                int studentSize = (int) Math.ceil(Double.parseDouble(students.size() + "") / 1000);
                for (int i = 0; i < studentSize; i++) {
                    List<Student> studentList;
                    if ((i + 1) == studentSize) {
                        studentList = students.subList(i * 1000, students.size());
                    } else {
                        studentList = students.subList(i * 1000, 1000);
                    }

                    mesData.put("tableName", "t_student");
                    mesData.put("data", studentList);
                    mesData.put("delFlag",0);
                    Message studentsMsg = new Message(RocketMqConstrants.Topic.bdxhTopic, RocketMqConstrants.Tags.userInfoTag_student, System.currentTimeMillis() + "", mesData.toJSONString().getBytes());
                    try {
                        defaultMQProducer.send(studentsMsg);
                    } catch (Exception e) {
                        log.info("学生用户信息推送失败");
                        e.printStackTrace();
                    }
                }
            }//老师用户信息
            List<Teacher> teachers = teacherControllerClient.findAllTeacher().getResult();
            if (teachers.size() != 0) {
                int teacherSize = (int) Math.ceil(Double.parseDouble(teachers.size() + "") / 1000);
                for (int i = 0; i < teacherSize; i++) {
                    List<Teacher> teacherList;
                    if ((i + 1) == teacherSize) {
                        teacherList = teachers.subList(i * 1000, teachers.size());
                    } else {
                        teacherList = teachers.subList(i * 1000, 1000);
                    }
                    mesData.put("tableName", "t_teacher");
                    mesData.put("data", teacherList);
                    mesData.put("delFlag",0);
                    Message teachersMsg = new Message(RocketMqConstrants.Topic.bdxhTopic, RocketMqConstrants.Tags.userInfoTag_teacher, System.currentTimeMillis() + "", mesData.toJSONString().getBytes());
                    try {
                        defaultMQProducer.send(teachersMsg);
                    } catch (Exception e) {
                        log.info("老师用户信息推送失败");
                        e.printStackTrace();
                    }
                }
            }

            //老师部门关系信息
            TeacherDept teacherDept = new TeacherDept();
            List<TeacherDept> teacherDeptList = teacherDeptControllerClient.findAllTeacherDeptInfo(teacherDept).getResult();
            if (teacherDeptList.size() != 0) {
                int teacherDeptSize = (int) Math.ceil(Double.parseDouble(teacherDeptList.size() + "") / 1000);
                for (int i = 0; i < teacherDeptSize; i++) {
                    List<TeacherDept> teacherDepts;
                    if ((i + 1) == teacherDeptSize) {
                        teacherDepts = teacherDeptList.subList(i * 1000, teacherDeptList.size());
                    } else {
                        teacherDepts = teacherDeptList.subList(i * 1000, 1000);
                    }
                    mesData.put("tableName", "t_teacher_dept");
                    mesData.put("data", teacherDepts);
                    mesData.put("delFlag",0);
                    Message teacherDeptListMsg = new Message(RocketMqConstrants.Topic.bdxhTopic, RocketMqConstrants.Tags.userInfoTag_teacherDept, System.currentTimeMillis() + "", mesData.toJSONString().getBytes());
                    try {
                        defaultMQProducer.send(teacherDeptListMsg);
                    } catch (Exception e) {
                        log.info("老师部门关系信息推送失败");
                        e.printStackTrace();
                    }
                }
            }

            //学校基本用户信息
            List<BaseUser> baseUserList = baseUserControllerClient.findAllBaseUserInfo().getResult();
            if (baseUserList.size() != 0) {
                int baseUserSize = (int) Math.ceil(Double.parseDouble(baseUserList.size() + "") / 1000);
                for (int i = 0; i < baseUserSize; i++) {
                    List<BaseUser> baseUsers;
                    if ((i + 1) == baseUserSize) {
                        baseUsers = baseUserList.subList(i * 1000, baseUserList.size());
                    } else {
                        baseUsers = baseUserList.subList(i * 1000, 1000);
                    }
                    mesData.put("tableName", "t_base_user");
                    mesData.put("data", baseUsers);
                    mesData.put("delFlag",0);
                    Message baseUserListMsg = new Message(RocketMqConstrants.Topic.bdxhTopic, RocketMqConstrants.Tags.userInfoTag_baseUser, System.currentTimeMillis() + "", mesData.toJSONString().getBytes());
                    try {
                        defaultMQProducer.send(baseUserListMsg);
                    } catch (Exception e) {
                        log.info("学校基本用户信息推送失败");
                        e.printStackTrace();
                    }
                }
            }


            //系统用户信息
            List<SchoolUser> schoolUserList = schoolUserControllerClient.findAllSchoolUserInfo().getResult();
            if (schoolUserList.size() != 0) {
                int schoolUserSize = (int) Math.ceil(Double.parseDouble(schoolUserList.size() + "") / 1000);
                for (int i = 0; i < schoolUserSize; i++) {
                    List<SchoolUser> schoolUsers;
                    if ((i + 1) == schoolUserSize) {
                        schoolUsers = schoolUserList.subList(i * 1000, schoolUserList.size());
                    } else {
                        schoolUsers = schoolUserList.subList(i * 1000, 1000);
                    }
                    mesData.put("tableName", "t_school_user");
                    mesData.put("data", schoolUsers);
                    mesData.put("delFlag",0);
                    Message schoolUserListMsg = new Message(RocketMqConstrants.Topic.bdxhTopic, RocketMqConstrants.Tags.schoolUserInfoTag_schoolUser, System.currentTimeMillis() + "", mesData.toJSONString().getBytes());
                    try {
                        defaultMQProducer.send(schoolUserListMsg);
                    } catch (Exception e) {
                        log.info("系统用户信息推送失败");
                        e.printStackTrace();
                    }
                }
            }


            //部门信息
            List<SchoolDept> schoolDepts = schoolDeptControllerClient.findSchoolDeptAll().getResult();
            if (schoolDepts.size() != 0) {
                int schoolDeptSize = (int) Math.ceil(Double.parseDouble(schoolDepts.size() + "") / 1000);
                for (int i = 0; i < schoolDeptSize; i++) {
                    List<SchoolDept> schoolDeptList;
                    if ((i + 1) == schoolDeptSize) {
                        schoolDeptList = schoolDepts.subList(i * 1000, schoolDepts.size());
                    } else {
                        schoolDeptList = schoolDepts.subList(i * 1000, 1000);
                    }
                    mesData.put("tableName", "t_school_dept");
                    mesData.put("data", schoolDeptList);
                    mesData.put("delFlag",0);
                    Message schoolDeptsMsg = new Message(RocketMqConstrants.Topic.bdxhTopic, RocketMqConstrants.Tags.schoolOrganizationTag_dept, System.currentTimeMillis() + "", mesData.toJSONString().getBytes());
                    try {
                        defaultMQProducer.send(schoolDeptsMsg);
                    } catch (Exception e) {
                        log.info("部门信息推送失败");
                        e.printStackTrace();
                    }
                }
            }


            //班级信息
            List<SchoolClass> schoolClasss = schoolClassControllerClient.findSchoolClassAll().getResult();
            if (schoolClasss.size() != 0) {
                int schoolClassSize = (int) Math.ceil(Double.parseDouble(schoolClasss.size() + "") / 1000);
                for (int i = 0; i < schoolClassSize; i++) {
                    List<SchoolClass> schoolClassList;
                    if ((i + 1) == schoolClassSize) {
                        schoolClassList = schoolClasss.subList(i * 1000, schoolClasss.size());
                    } else {
                        schoolClassList = schoolClasss.subList(i * 1000, 1000);
                    }
                    mesData.put("tableName", "t_school_class");
                    mesData.put("data", schoolClassList);
                    mesData.put("delFlag",0);
                    Message schoolClassesMsg = new Message(RocketMqConstrants.Topic.bdxhTopic, RocketMqConstrants.Tags.schoolOrganizationTag_class, System.currentTimeMillis() + "", mesData.toJSONString().getBytes());
                    try {
                        defaultMQProducer.send(schoolClassesMsg);
                    } catch (Exception e) {
                        log.info("班级信息推送失败");
                        e.printStackTrace();
                    }
                }
            }

            try {

            } catch (Exception e) {
                e.printStackTrace();
                log.info("=======================推送异常=======================");
            }
            log.info("=======================推送结束=======================");
        }*/
    }
}