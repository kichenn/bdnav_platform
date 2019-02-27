package com.bdxh.school.persistence;

import com.bdxh.school.entity.SchoolClass;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface SchoolClassMapper extends Mapper<SchoolClass> {

    //parentId查询学校关系信息
    List<SchoolClass> findSchoolClassByParentId(@Param("parentId") Long parentId);

    //id批量删除学校关系信息
    Integer batchDelSchoolClassInIds(@Param("ids") List<Long> ids);

    //学校id删除该学校底下所有关系信息
    Integer delSchoolClassBySchoolId(@Param("schoolId") Long schoolId);
}