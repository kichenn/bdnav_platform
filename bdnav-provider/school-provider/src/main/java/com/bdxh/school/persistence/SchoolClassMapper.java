package com.bdxh.school.persistence;

import com.bdxh.school.entity.SchoolClass;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface SchoolClassMapper extends Mapper<SchoolClass> {


    //id批量删除学校关系信息
    Integer batchDelSchoolClassInIds(@Param("ids") List<Long> ids);

    //学校id删除该学校底下所有关系信息
    Integer delSchoolClassBySchoolId(@Param("schoolId") Long schoolId);

    //根据条件查询院校信息
    SchoolClass findSchoolClassByNameAndSchoolCode(@Param("schoolCode") String schoolCode, @Param("name") String name);

    //父id查询院系信息
    SchoolClass findSchoolByParentId(@Param("parentId") Long parentId);
}