package com.bdxh.school.service;

import com.bdxh.common.support.IService;
import com.bdxh.school.dto.SchoolClassDto;
import com.bdxh.school.dto.SchoolClassModifyDto;
import com.bdxh.school.entity.SchoolClass;

import java.util.List;
import java.util.Optional;

/**
 * @Description: 学校院校关系信息
 * @Author: Kang
 * @Date: 2019/2/26 17:41
 */
public interface SchoolClassService extends IService<SchoolClass> {


    //增加院校关系
    Boolean addSchoolClass(SchoolClassDto schoolClassDto);

    //修改院校关系
    Boolean modifySchoolClass(SchoolClassModifyDto schoolClassDto);

    //id删除院校关系
    Boolean delSchoolClassById(Long id);

    //批id量删除院校关系
    Boolean batchDelSchoolClassInIds(List<Long> ids);

    //删除院校底下信息
    Boolean delSchoolClassBySchoolId(Long schoolId);

    //学校id查询等级节点列表（一级节点为父节点）
    List<SchoolClass> findSchoolParentClassBySchoolId(Long schoolId);

    //id查询信息
    Optional<SchoolClass> findSchoolClassById(Long id);

    //查询所有信息
    List<SchoolClass> findSchoolClassAll();

    //根据条件查询院校关系
    SchoolClass findSchoolClassBySchoolClass(SchoolClass schoolClass);

    //父id查询院系信息
    List<SchoolClass> findSchoolByParentId(Long parentId);

    //查询单个学校院系路径和Ids
    List<SchoolClass> queryClassBySchoolCode(String schoolCode);


}
