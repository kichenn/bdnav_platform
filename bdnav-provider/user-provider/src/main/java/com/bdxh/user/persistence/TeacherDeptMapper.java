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
    int deleteTeacherDept(@Param("schoolCode") String schoolCode, @Param("cardNumber") String cardNumber);

    List<TeacherDeptVo> selectTeacherDeptDetailsInfo(@Param("schoolCode") String schoolCode, @Param("cardNumber") String cardNumber);

    TeacherDeptDto updateTeacherDept(TeacherDeptDto teacherDeptDto);

    //根据部门ID统计老师人数
    int queryTeacherCount(String schoolCode, String deptId);

    //学校code，学校id，部门id查询老师信息
    TeacherDept findTeacherBySchoolDeptId(@Param("schoolCode") String schoolCode, @Param("schoolId") Long schoolId, @Param("deptId") Long deptId);
}