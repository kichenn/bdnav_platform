package com.bdxh.school.service;

import com.bdxh.common.helper.tree.bean.TreeBean;
import com.bdxh.common.support.IService;
import com.bdxh.school.dto.ClassAdministratorsUpdateDto;
import com.bdxh.school.dto.SchoolOrgAddDto;
import com.bdxh.school.dto.SchoolOrgQueryDto;
import com.bdxh.school.dto.SchoolOrgUpdateDto;
import com.bdxh.school.entity.SchoolOrg;
import com.bdxh.school.vo.SchoolOrgTreeVo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Description: 业务层接口
 * @Author Kang
 * @Date 2019-05-31 14:06:08
 */
@Service
public interface SchoolOrgService extends IService<SchoolOrg> {

    //查询所有数量
    Integer getSchoolOrgAllCount();

     //批量删除方法
    Boolean batchDelSchoolOrgInIds(List<Long> id);

    //根据条件查询所有的学校组织架构信息
    List<SchoolOrg> findAllSchoolOrgInfo(SchoolOrgQueryDto schoolOrgQueryDto);

    //根据条件查询单个学校组织架构信息
    SchoolOrg findSchoolOrgInfo(Long id);

    //根据学校Id查询学生组织架构信息
    List<SchoolOrg> findClassOrg(Long schoolId);

    //根据ID删除组织架构信息
    Boolean removeSchoolOrgInfo(Long id);

    //修改组织架构信息
    Boolean updateSchoolOrgInfo(SchoolOrgUpdateDto schoolOrgUpdateDto);

    //新增组织架构信息
    Boolean insertSchoolOrgInfo(SchoolOrgAddDto schoolOrgAddDto);

    //查询所有组织架构信息
    List<SchoolOrg> findAllOrgInfo();

    //根据父ID查询学校组织架构信息
    List<SchoolOrg> findBySchoolOrgByParentId(Long parentId);

    //修改班级管理员信息
    Boolean updateSchoolClassInfo(ClassAdministratorsUpdateDto classAdministratorsUpdateDto);

    //查询出老师的树形结构数据
    List<SchoolOrg> findTeacherDeptInfo(Long schoolId);

    //根据学校Id查询学校学生的组织架构层级树
    List<SchoolOrgTreeVo> findClassOrgLoopTree(Long schoolId);
}
