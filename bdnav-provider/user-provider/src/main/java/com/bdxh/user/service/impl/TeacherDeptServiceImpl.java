package com.bdxh.user.service.impl;

import com.bdxh.common.support.BaseService;
import com.bdxh.common.support.IService;
import com.bdxh.user.entity.TeacherDept;
import com.bdxh.user.persistence.TeacherDeptMapper;
import com.bdxh.user.service.TeacherDeptService;
import com.bdxh.user.service.TeacherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public void deleteTeacherDeptInfo(String schoolCode, String cardNumber, String id) {
        teacherDeptMapper.deleteTeacherDept(schoolCode, cardNumber);
    }
}
