package com.bdxh.user.service;

import com.bdxh.common.web.support.IService;
import com.bdxh.user.dto.StudentQueryDto;
import com.bdxh.user.entity.Student;
import com.github.pagehelper.PageInfo;

/**
 * @description: 学生信息service
 * @author: xuyuan
 * @create: 2019-02-26 10:38
 **/
public interface StudentService extends IService<Student> {

    //根据条件分页查询学生信息
    PageInfo<Student> getStudentList(StudentQueryDto studentQueryDto);

    //根据id删除学生信息以及学生家长绑定信息
    void deleteStudentInfo(String id);

    //根据id批量删除学生信息以及学生家长绑定信息
    void deleteBatchesStudentInfo(String id[]);
}
