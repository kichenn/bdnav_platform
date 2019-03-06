package com.bdxh.user.persistence;

import com.bdxh.user.entity.FamilyStudent;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface FamilyStudentMapper extends Mapper<FamilyStudent> {
    //根据家长ID查询出关系表的数据
    @Select("select * from t_family_student where family_id =#{family_id}")
    List<FamilyStudent> getFamilyStudentByFamilyId(@Param("family_id") Long familyId);

    //修改学生家长关系表的信息
    int updateFamilyStudentInfo(FamilyStudent familyStudent);
}