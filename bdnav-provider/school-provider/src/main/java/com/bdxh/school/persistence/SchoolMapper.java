package com.bdxh.school.persistence;

import com.bdxh.school.dto.SchoolDto;
import com.bdxh.school.dto.SchoolQueryDto;
import com.bdxh.school.entity.School;
import com.bdxh.school.vo.BaseEchartsVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface SchoolMapper extends Mapper<School> {

    //条件查询
    List<School> findIdsInCondition(@Param("schoolDto") SchoolQueryDto schoolDto);

    //批量查询
    List<School> findSchoolInIds(@Param("ids") List<Long> ids);

    //批量删除
    Integer batchDelSchool(@Param("ids") List<Long> ids);

    //根据Code查询学校
    School findSchoolBySchoolCode(@Param("schoolCode")String schoolCode);

    //查询不同地区下的学校的数量
    List<BaseEchartsVo> querySchoolNumByArea();
}