package com.bdxh.school.service;

import com.bdxh.common.web.support.IService;
import com.bdxh.school.dto.ModifySchoolDto;
import com.bdxh.school.dto.SchoolDto;
import com.bdxh.school.entity.School;
import com.bdxh.school.vo.SchoolVo;
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
    @Transactional(rollbackFor = Exception.class)
    Boolean addSchool(SchoolDto schoolDto);

    //修改学校信息
    @Transactional(rollbackFor = Exception.class)
    Boolean modifySchool(ModifySchoolDto school);

    //删除学校信息
    @Transactional(rollbackFor = Exception.class)
    Boolean delSchool(Long id);

    //id查询学校信息
    Optional<School> findSchoolById(Long id);

    //筛选条件查询学校id
    Optional<List<String>> findSchoolIds(String schoolCode, String schoolName, Integer page, Integer limit);

    //ids查询学校列表
    Optional<List<School>> findSchoolsByIds(List<String> ids);
}
