package com.bdxh.school.service.impl;

import com.bdxh.common.helper.tree.bean.TreeBean;
import com.bdxh.common.helper.tree.utils.LongUtils;
import com.bdxh.common.helper.tree.utils.TreeLoopUtils;
import com.bdxh.common.support.BaseService;
import com.bdxh.school.dto.SchoolDeptDto;
import com.bdxh.school.dto.SchoolDeptModifyDto;
import com.bdxh.school.entity.SchoolDept;
import com.bdxh.school.persistence.SchoolDeptMapper;
import com.bdxh.school.service.SchoolDeptService;
import com.bdxh.school.vo.SchoolDeptTreeVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Description: 学校组织Service
 * @Author: Kang
 * @Date: 2019/2/27 15:05
 */
@Service
@Slf4j
public class SchoolDeptServiceImpl extends BaseService<SchoolDept> implements SchoolDeptService {

    @Autowired
    private SchoolDeptMapper schoolDeptMapper;

    //增加学校组织
    @Override
    public Boolean addSchoolDept(SchoolDeptDto schoolDeptDto) {
        SchoolDept schoolDept = new SchoolDept();
        BeanUtils.copyProperties(schoolDeptDto, schoolDept);
        if (new Long("-1").equals(schoolDept.getParentId())) {
            schoolDept.setParentNames("");
            schoolDept.setThisUrl(schoolDept.getName());
            schoolDept.setParentIds("");
        } else {
            //查询父亲节点
            SchoolDept schoolDeptTemp = findSchoolDeptById(schoolDeptDto.getParentId()).orElse(new SchoolDept());
            //树状
            schoolDept.setParentNames(schoolDeptTemp.getParentNames() + "/" + schoolDeptTemp.getName());
            schoolDept.setThisUrl(schoolDeptTemp.getParentNames() + "/" + schoolDeptTemp.getName() + "/" + schoolDept.getName());
            schoolDept.setParentIds(schoolDeptTemp.getParentIds() + "," + schoolDeptTemp.getId());
        }
        return schoolDeptMapper.insertSelective(schoolDept) > 0;
    }

    //修改学校组织信息
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean modifySchoolDept(SchoolDeptModifyDto schoolDeptDto) {
        SchoolDept schoolDept = new SchoolDept();
        BeanUtils.copyProperties(schoolDeptDto, schoolDept);
        if (new Long("-1").equals(schoolDept.getParentId())) {
            schoolDept.setParentNames("");
            schoolDept.setThisUrl(schoolDept.getName());
            schoolDept.setParentIds("");
        } else {
            //查询父亲节点
            SchoolDept schoolDeptTemp = findSchoolDeptById(schoolDeptDto.getParentId()).orElse(new SchoolDept());
            //树状
            schoolDept.setParentNames(schoolDeptTemp.getParentNames() + "/" + schoolDeptTemp.getName());
            schoolDept.setThisUrl(schoolDeptTemp.getParentNames() + "/" + schoolDeptTemp.getName() + "/" + schoolDept.getName());
            schoolDept.setParentIds(schoolDeptTemp.getParentIds() + "," + schoolDeptTemp.getId());
        }

        //查询当前节点的子节点
        // 修改当前组织，  子部门组织的 url parentnames 要跟着修改
        List<SchoolDept> depts = findSchoolByParentId(schoolDept.getId());
        if (CollectionUtils.isNotEmpty(depts)) {
            depts.forEach(e -> {
                e.setParentNames(schoolDept.getParentNames() + "/" + schoolDept.getName());
                e.setThisUrl(schoolDept.getParentNames() + "/" + schoolDept.getName() + "/" + e.getName());
                schoolDeptMapper.updateByPrimaryKeySelective(e);
            });
        }

        return schoolDeptMapper.updateByPrimaryKeySelective(schoolDept) > 0;
    }

    //删除学校组织信息
    @Override
    public Boolean delSchoolDeptById(Long id) {
        return schoolDeptMapper.deleteByPrimaryKey(id) > 0;
    }

    //批量删除学校组织信息
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean batchDelSchoolDeptInIds(List<Long> ids) {
        return schoolDeptMapper.batchDelSchoolDeptInIds(ids) > 0;
    }

    //删除学校组织信息By SchoolId
    @Override
    public Boolean delSchoolDeptBySchoolId(Long schoolId) {
        return schoolDeptMapper.delSchoolDeptBySchoolId(schoolId) > 0;
    }

    //学校id查询等级节点列表（一级节点为父节点）
    @Override
    public List<SchoolDept> findSchoolParentDeptBySchoolId(Long schoolId) {
        SchoolDept schoolDept = new SchoolDept();
        schoolDept.setSchoolId(schoolId);
        return schoolDeptMapper.select(schoolDept);
    }

    //id查询信息
    @Override
    public Optional<SchoolDept> findSchoolDeptById(Long id) {
        return Optional.ofNullable(schoolDeptMapper.selectByPrimaryKey(id));
    }

    //查询所有信息
    @Override
    public List<SchoolDept> findSchoolDeptAll() {
        return schoolDeptMapper.selectAll();
    }

    //父级id查询部门信息
    @Override
    public List<SchoolDept> findSchoolByParentId(Long parentId) {
        return schoolDeptMapper.findSchoolByParentId(parentId);
    }
}
