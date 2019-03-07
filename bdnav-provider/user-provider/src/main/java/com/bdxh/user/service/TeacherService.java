package com.bdxh.user.service;

import com.bdxh.common.web.support.IService;
import com.bdxh.user.dto.TeacherDto;
import com.bdxh.user.dto.TeacherQueryDto;
import com.bdxh.user.entity.Teacher;
import com.github.pagehelper.PageInfo;

/**
 * @description: 老师信息service
 * @author: xuyuan
 * @create: 2019-02-26 10:39
 **/
public interface TeacherService extends IService<Teacher> {

    /**
     * @Author： binzh
     * @Description： //根据条件分页查询老师信息
     * @Date： 14:49 2019/3/6
     * @Param： [teacherQueryDto]
     * @return： com.github.pagehelper.PageInfo<com.bdxh.user.entity.Teacher>
     **/
    PageInfo<Teacher> getTeacherList(TeacherQueryDto teacherQueryDto);


    /**
     * @Author： binzh
     * @Description： //根据id删除老师信息以及老师部门关系绑定信息
     * @Date： 14:49 2019/3/6
     * @Param： [id]
     * @return： void
     **/
    void deleteTeacherInfo(String id);

    /**
     * @Author： binzh
     * @Description： //根据id批量删除老师信息以及老师部门关系绑定信息
     * @Date： 14:49 2019/3/6
     * @Param： [id]
     * @return： void
     **/
    void deleteBatchesTeacherInfo(String id[]);

    /**
     * @Author： binzh
     * @Description： //保存老师信息以及绑定部门信息
     * @Date： 15:15 2019/3/6
     * @Param： [teacherDto]
     * @return： void
     **/
    void saveTeacherDeptInfo(TeacherDto teacherDto);
}
