package com.bdxh.user.persistence;

import com.bdxh.user.entity.TeacherDept;
import com.bdxh.user.vo.TeacherDeptVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

@Repository
public interface TeacherDeptMapper extends Mapper<TeacherDept> {
    int deleteTeacherDept(@Param("schoolCode") String schoolCode, @Param("cardNumber") String cardNumber,@Param("deptId")Integer deptId);

    List<TeacherDeptVo> selectTeacherDeptDetailsInfo(@Param("schoolCode") String schoolCode, @Param("cardNumber") String cardNumber);

    //根据部门ID统计老师人数
    int queryTeacherCount(String schoolCode, String deptId);

    //学校code，学校id，部门id查询老师信息
    TeacherDept findTeacherBySchoolDeptId(@Param("schoolCode") String schoolCode, @Param("schoolId") Long schoolId, @Param("deptId") Long deptId);

    //批量删除老师部门关系信息
    int batchRemoveTeacherDeptInfo(@Param("list") List<Map<String,String>> list);

    //获取老师部门关系
    TeacherDept findTeacherBySchoolCodeAndCardNumber(@Param("schoolCode") String schoolCode, @Param("cardNumber") String cardNumber);
}