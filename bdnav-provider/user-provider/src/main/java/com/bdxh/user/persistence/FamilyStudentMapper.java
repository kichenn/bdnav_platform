package com.bdxh.user.persistence;

import com.bdxh.user.dto.FamilyDto;
import com.bdxh.user.dto.FamilyStudentDto;
import com.bdxh.user.entity.FamilyStudent;
import com.bdxh.user.vo.FamilyStudentVo;
import com.bdxh.user.vo.FamilyVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface FamilyStudentMapper extends Mapper<FamilyStudent> {

    //学生删除家长学生关系数据
    int studentRemoveFamilyStudentInfo(@Param("schoolCode")String schoolCode,@Param("studentNumber")String studentNumber);

    //家长删除关系表数据
    int familyRemoveFamilyStudent(@Param("schoolCode")String schoolCode,@Param("cardNumber")String cardNumber);

    //家长查询绑定关系
    List<FamilyStudentVo> selectFamilyStudentInfo(@Param("schoolCode")String schoolCode,@Param("cardNumber")String cardNumber);

    //学生查询关系表数据
    FamilyStudentVo studentQueryInfo(@Param("schoolCode")String schoolCode,@Param("cardNumber")String cardNumber);


    //修改学生名称
    int updateStudentInfo(@Param("familyStudentDto") FamilyStudentDto familyStudentDto);
}