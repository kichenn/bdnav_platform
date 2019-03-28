package com.bdxh.user.persistence;

import com.bdxh.user.dto.AddFamilyStudentDto;
import com.bdxh.user.dto.FamilyStudentQueryDto;
import com.bdxh.user.entity.FamilyStudent;
import com.bdxh.user.vo.FamilyStudentVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface FamilyStudentMapper extends Mapper<FamilyStudent> {

    //学生删除家长学生关系数据
    int studentRemoveFamilyStudentInfo(@Param("schoolCode")String schoolCode,@Param("studentNumber")String studentNumber);

    //家长删除关系表数据
    int familyRemoveFamilyStudent(@Param("schoolCode")String schoolCode,@Param("cardNumber")String cardNumber,@Param("id")String id);

    //家长查询绑定关系
    List<FamilyStudentVo> selectFamilyStudentInfo(@Param("schoolCode")String schoolCode,@Param("cardNumber")String cardNumber);

    //根据条件查询学生家长关系
    List<FamilyStudentVo> queryaAllFamilyStudent(@Param("familyStudentQueryDto") FamilyStudentQueryDto familyStudentQueryDto);

    //学生查询关系表数据
    FamilyStudentVo studentQueryInfo(@Param("schoolCode")String schoolCode,@Param("cardNumber")String cardNumber);

    //修改学生名称
    int updateStudentInfo(@Param("addFamilyStudentDto") AddFamilyStudentDto addFamilyStudentDto);
}