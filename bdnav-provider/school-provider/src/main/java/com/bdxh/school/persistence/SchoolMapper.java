package com.bdxh.school.persistence;

import com.bdxh.school.entity.School;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface SchoolMapper extends Mapper<School> {

    List<School> findIdsInCondition(@Param("school_code") String school_code, @Param("school_name") String school_name);

//    List<School> findInfoInIds(@Param("ids") List<String> ids);
}