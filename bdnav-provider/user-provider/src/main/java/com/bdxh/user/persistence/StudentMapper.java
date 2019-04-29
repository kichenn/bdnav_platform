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
import java.util.Map;

@Repository
public interface StudentMapper extends Mapper<Student> {

    //删除学生信息
    int removeStudentInfo(@Param("schoolCode") String schoolCode, @Param("cardNumber") String cardNumber);

    //查询单个学生信息
    StudentVo selectStudentVo(@Param("schoolCode") String schoolCode, @Param("cardNumber") String cardNumber);

    //修改学生信息
    int updateStudentInfo(@Param("student") Student student);

    //查询所有学生
    List<Student> selectAllStudentInfo(@Param("studentQueryDto") StudentQueryDto studentQueryDto);

    //学校code，学校id，班级id查询学生信息
    Student findStudentBySchoolClassId(@Param("schoolCode") String schoolCode, @Param("schoolId") Long schoolId, @Param("classId") Long classId);

    //批量删除学生信息
    int batchRemoveStudentInfo(@Param("list") List<Map<String,String>> list);

    //批量新增学生信息
    int batchSaveStudentInfo(List<Student> studentList);

    //根据学校Code查询所有学生学号
    List<String> queryCardNumberBySchoolCode(@Param("schoolCode") String schoolCode);

    //根据学校CODE和组织架构查询学生
    List<Student> findStudentInfoByClassOrg(@Param("schoolCode")String schoolCode,@Param("parentIds")String parentIds,@Param("type")Byte type);

    //查询单个学生
    Student findStudentInfo(@Param("schoolCode")String schoolCode,@Param("cardNumber")String cardNumber);
}
