package com.bdxh.user.persistence;

import com.bdxh.user.dto.StudentQueryDto;
import com.bdxh.user.dto.UpdateStudentDto;
import com.bdxh.user.entity.Student;
import com.bdxh.user.vo.StudentVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface StudentMapper extends Mapper<Student> {

    //删除学生信息
    int removeStudentInfo(@Param("schoolCode") String schoolCode, @Param("cardNumber") String cardNumber);

    //查询单个学生信息
    StudentVo selectStudentVo(@Param("schoolCode") String schoolCode, @Param("cardNumber") String cardNumber);

    //修改学生信息
    int updateStudentInfo(@Param("studentDto") UpdateStudentDto updateStudentDto);

    //查询所有学生
    List<Student> selectAllStudentInfo(@Param("studentQueryDto") StudentQueryDto studentQueryDto);

    //学校code，学校id，班级id查询学生信息
    Student findStudentBySchoolClassId(@Param("schoolCode") String schoolCode, @Param("schoolId") Long schoolId, @Param("classId") Long classId);
}