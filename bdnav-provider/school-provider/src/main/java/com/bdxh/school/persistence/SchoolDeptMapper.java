package com.bdxh.school.persistence;

import com.bdxh.school.entity.SchoolDept;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface SchoolDeptMapper extends Mapper<SchoolDept> {

    //id批量删除学校组织信息
    Integer batchDelSchoolDeptInIds(@Param("ids") List<Long> ids);

    //schoolId删除学校组织信息
    Integer delSchoolDeptBySchoolId(@Param("schoolId") Long schoolId);


    List<SchoolDept> findSchoolByParentId(@Param("parentId") Long parentId);
}