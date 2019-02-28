package com.bdxh.school.service;

import com.bdxh.common.web.support.IService;
import com.bdxh.school.dto.SchoolDeptDto;
import com.bdxh.school.dto.SchoolDeptModifyDto;
import com.bdxh.school.entity.SchoolClass;
import com.bdxh.school.entity.SchoolDept;
import com.bdxh.school.vo.SchoolClassVo;
import com.bdxh.school.vo.SchoolDeptVo;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.List;
import java.util.Optional;


/**
 * @Description: 学校组织
 * @Author: Kang
 * @Date: 2019/2/27 14:56
 */
public interface SchoolDeptService extends IService<SchoolDept> {

    //增加学校组织信息
    Boolean addSchoolDept(SchoolDeptDto schoolDeptDto);

    //修改学校组织信息
    Boolean modifySchoolDept(SchoolDeptModifyDto schoolDeptDto);

    //删除学校组织信息
    Boolean delSchoolDeptById(Long id);

    //批量删除学校组织信息
    Boolean batchDelSchoolDeptInIds(List<Long> ids);

    //删除学校组织信息By SchoolId
    Boolean delSchoolDeptBySchoolId(Long schoolId);

    //递归查询关系节点
    List<SchoolDeptVo> findSchoolDeptRelation(SchoolDeptVo schoolDeptVo);

    //学校id查询等级节点列表（一级节点为父节点）
    List<SchoolDept> findSchoolParentDeptBySchoolId(Long schoolId, Byte level);

    //关系id查询等级信息
    List<SchoolDeptVo> findSchoolDeptByParentId(Long parentId);

    //id查询信息
    Optional<SchoolDept> findSchoolDeptById(Long id);

    //查询所有信息
    List<SchoolDept> findSchoolDeptAll();
}
