package com.bdxh.user.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.bdxh.common.base.constant.RocketMqConstrants;
import com.bdxh.common.support.BaseService;
import com.bdxh.common.support.IService;
import com.bdxh.user.entity.TeacherDept;
import com.bdxh.user.persistence.TeacherDeptMapper;
import com.bdxh.user.service.TeacherDeptService;
import com.bdxh.user.service.TeacherService;
import com.xiaoleilu.hutool.collection.CollUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.json.JsonObject;
import java.util.List;

/**
 * @description: 老师部门关联表service实现
 * @author: xuyuan
 * @create: 2019-02-26 10:46
 **/
@Service
@Slf4j
public class TeacherDeptServiceImpl extends BaseService<TeacherDept> implements TeacherDeptService {
    @Autowired
    private TeacherDeptMapper teacherDeptMapper;

    @Autowired
    private DefaultMQProducer defaultMQProducer;

    @Override
    public void deleteTeacherDeptInfo(String schoolCode, String cardNumber, Integer deptId) {
        teacherDeptMapper.deleteTeacherDept(schoolCode, cardNumber, deptId);
    }

    /**
     * @Description: 学校code，学校id，部门id查询老师信息
     * @Author: Kang
     * @Date: 2019/3/23 11:40
     */
    @Override
    public TeacherDept findTeacherBySchoolDeptId(String schoolCode, Long schoolId, Long deptId) {
        return teacherDeptMapper.findTeacherBySchoolDeptId(schoolCode, schoolId, deptId);
    }

    @Override
    public List<TeacherDept> findTeacherDeptsBySchoolCode(String schoolCode, String deptId, String type) {
        return teacherDeptMapper.findTeacherDeptsBySchoolCode(schoolCode, deptId, type);
    }

    @Override
    public void batchUpdateTeacherDept(List<TeacherDept> teacherDepts) {
        Boolean result=false;
        if(CollUtil.isNotEmpty(teacherDepts)){
         result = teacherDeptMapper.batchUpdateTeacherDept(teacherDepts) > 0;
        }
        //修改完成之后同步到第三方给第三方修改
        if (result) {
            JSONObject mesData = new JSONObject();
            mesData.put("tableName", "t_teacher_dept");
            mesData.put("data", teacherDepts);
            mesData.put("del_flag", "0");
            Message baseUserMsg = new Message(RocketMqConstrants.Topic.bdxhTopic, RocketMqConstrants.Tags.userInfoTag_teacherDept, String.valueOf(System.currentTimeMillis()), mesData.toJSONString().getBytes());
            try {
                defaultMQProducer.send(baseUserMsg);
            } catch (Exception e) {
                e.printStackTrace();
                log.info("批量修改组织架构时推送失败");
            }
        }
    }
}
