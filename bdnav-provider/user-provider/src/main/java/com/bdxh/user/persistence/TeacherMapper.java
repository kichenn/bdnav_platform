package com.bdxh.user.persistence;

import com.bdxh.user.dto.TeacherDto;
import com.bdxh.user.entity.Teacher;
import com.bdxh.user.vo.TeacherVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;
@Repository
public interface TeacherMapper extends Mapper<Teacher> {
    int deleteTeacher(@Param("schoolCode")String schoolCode, @Param("cardNumber")String cardNumber);

    TeacherVo selectTeacherDetails(@Param("schoolCode")String schoolCode, @Param("cardNumber")String cardNumber);

    int updateTeacher(TeacherDto teacherDto);

}