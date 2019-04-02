package com.bdxh.user.persistence;

import com.bdxh.user.dto.AddTeacherDto;
import com.bdxh.user.dto.TeacherQueryDto;
import com.bdxh.user.dto.UpdateTeacherDto;
import com.bdxh.user.entity.Teacher;
import com.bdxh.user.vo.TeacherVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

@Repository
public interface TeacherMapper extends Mapper<Teacher> {
    int deleteTeacher(@Param("schoolCode")String schoolCode, @Param("cardNumber")String cardNumber);

    Teacher selectTeacherDetails(@Param("schoolCode")String schoolCode, @Param("cardNumber")String cardNumber);

    int updateTeacher(@Param("teacher") Teacher teacher);

    List<Teacher> selectAllTeacherInfo(@Param("teacherQueryDto")TeacherQueryDto teacherQueryDto);

    //批量删除老师信息
    int batchRemoveTeacherInfo(@Param("list") List<Map<String,String>> list);

    //批量新增老师
    int  batchSaveTeacherInfo(List<Teacher> teacherList);

    //根据学校Code查询卡号
    List<String> queryTeacherCardNumberBySchoolCode(@Param("schoolCode")String schoolCode);
}