package com.bdxh.user.persistence;

import com.bdxh.user.dto.TeacherDeptDto;
import com.bdxh.user.entity.TeacherDept;
import com.bdxh.user.vo.TeacherDeptVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface TeacherDeptMapper extends Mapper<TeacherDept> {
    int deleteTeacherDept(@Param("schoolCode")String schoolCode, @Param("cardNumber")String cardNumber);

    List<TeacherDeptVo> selectTeacherDeptDetailsInfo(@Param("schoolCode")String schoolCode, @Param("cardNumber")String cardNumber);

    TeacherDeptDto updateTeacherDept(TeacherDeptDto teacherDeptDto);
}