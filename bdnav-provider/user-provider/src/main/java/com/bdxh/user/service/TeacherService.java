package com.bdxh.user.service;

import com.bdxh.common.web.support.IService;
import com.bdxh.user.dto.TeacherQueryDto;
import com.bdxh.user.entity.Teacher;
import com.github.pagehelper.PageInfo;

/**
 * @description: 老师信息service
 * @author: xuyuan
 * @create: 2019-02-26 10:39
 **/
public interface TeacherService extends IService<Teacher> {

    //根据条件分页查询老师信息
    PageInfo<Teacher> getTeacherList(TeacherQueryDto teacherQueryDto);

    //根据id删除老师信息以及老师部门关系绑定信息
    void deleteTeacherInfo(String id);

    //根据id批量删除老师信息以及老师部门关系绑定信息
    void deleteBatchesTeacherInfo(String id[]);
}
