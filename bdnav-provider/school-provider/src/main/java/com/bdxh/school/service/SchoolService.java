package com.bdxh.school.service;

import com.bdxh.common.web.support.IService;
import com.bdxh.school.dto.ModifySchoolDto;
import com.bdxh.school.dto.SchoolDto;
import com.bdxh.school.dto.SchoolQueryDto;
import com.bdxh.school.entity.School;
import com.bdxh.school.vo.SchoolVo;
import com.github.pagehelper.PageInfo;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.util.List;
import java.util.Optional;

/**
 * @Description: 学校基本信息
 * @Author: Kang
 * @Date: 2019/2/25 16:33
 */
public interface SchoolService extends IService<School> {

    String SCHOOL_INFO_PREFIX = "SCHOOL_INFO";

    String SCHOOL_LIST_PREFIX = "SCHOOL_LIST";

    //增加学校信息
    Boolean addSchool(SchoolDto schoolDto);

    //修改学校信息
    Boolean modifySchool(ModifySchoolDto school);

    //删除学校信息
    Boolean delSchool(Long id);

    //批量删除学校信息
    Boolean batchDelSchool(List<Long> id);

    //id查询学校信息
    Optional<School> findSchoolById(Long id);

    //筛选条件查询学校列表
    PageInfo<School> findSchoolsInCondition(SchoolQueryDto schoolQueryDto);

    //查询学校列表（全部，无条件）
    List<School> findSchools();
}
