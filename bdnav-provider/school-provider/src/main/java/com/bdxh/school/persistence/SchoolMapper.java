package com.bdxh.school.persistence;

import com.bdxh.school.entity.School;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface SchoolMapper extends Mapper<School> {

    List<School> findIdsInCondition(@Param("schoolCode") String schoolCode, @Param("schoolName") String schoolName);

    Integer batchDelSchool(@Param("ids") List<Long> ids);
}