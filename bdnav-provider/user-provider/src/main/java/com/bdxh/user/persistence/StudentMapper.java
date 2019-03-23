package com.bdxh.user.persistence;

import com.bdxh.user.dto.StudentQueryDto;
import com.bdxh.user.dto.UpdateStudentDto;
import com.bdxh.user.entity.Student;
import com.bdxh.user.vo.StudentVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface StudentMapper extends Mapper<Student> {

    //删除学生信息
    int removeStudentInfo(@Param("schoolCode")String schoolCode,@Param("cardNumber")String cardNumber);

    //查询单个学生信息
    StudentVo selectStudentVo(@Param("schoolCode")String schoolCode,@Param("cardNumber")String cardNumber);

    //修改学生信息
    int updateStudentInfo(@Param("studentDto") UpdateStudentDto updateStudentDto);

    //查询所有学生
    List<Student> selectAllStudentInfo(@Param("studentQueryDto")StudentQueryDto studentQueryDto);

    //根据学校CODE和班级ID统计学生人数
    int statisticsStuByClassIds(@Param("schoolCode")String schoolCode,@Param("classId")Long classId);

}