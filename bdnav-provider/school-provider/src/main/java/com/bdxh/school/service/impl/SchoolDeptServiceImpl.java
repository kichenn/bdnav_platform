package com.bdxh.school.service.impl;

import com.bdxh.common.web.support.BaseService;
import com.bdxh.school.dto.SchoolDeptDto;
import com.bdxh.school.dto.SchoolDeptModifyDto;
import com.bdxh.school.entity.SchoolDept;
import com.bdxh.school.persistence.SchoolDeptMapper;
import com.bdxh.school.service.SchoolDeptService;
import com.bdxh.school.helper.utils.LongUtils;
import com.bdxh.school.vo.SchoolDeptVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        return schoolDeptMapper.insertSelective(schoolDept) > 0;
    }

    //修改学校组织信息
    @Override
    public Boolean modifySchoolDept(SchoolDeptModifyDto schoolDeptDto) {
        SchoolDept schoolDept = new SchoolDept();
        BeanUtils.copyProperties(schoolDeptDto, schoolDept);
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

    //递归查询关系节点
    @Override
    public List<SchoolDeptVo> findSchoolDeptRelation(SchoolDeptVo schoolDeptVo) {
        List<SchoolDeptVo> schoolDeptVos = new ArrayList<>();
        if (LongUtils.isNotEmpty(schoolDeptVo.getId())) {
            schoolDeptVos.addAll(findSchoolDeptByParentId(schoolDeptVo.getId()));
            if (CollectionUtils.isNotEmpty(schoolDeptVos)) {
                schoolDeptVos.stream().forEach(e -> {
                    if (LongUtils.isNotEmpty(e.getId())) {
                        e.setSchoolDeptVos(findSchoolDeptRelation(e));
                    }
                });
            }
        }
        return schoolDeptVos;
    }

    //学校id查询等级节点列表（一级节点为父节点）
    @Override
    public List<SchoolDept> findSchoolParentDeptBySchoolId(Long schoolId, Byte level) {
        SchoolDept schoolDept = new SchoolDept();
        schoolDept.setLevel(level);
        schoolDept.setSchoolId(schoolId);
        return schoolDeptMapper.select(schoolDept);
    }

    //关系id查询等级信息
    @Override
    public List<SchoolDeptVo> findSchoolDeptByParentId(Long parentId) {
        List<SchoolDeptVo> schoolDeptVos = new ArrayList<>();
        List<SchoolDept> schoolDepts = schoolDeptMapper.findSchoolDeptByParentId(parentId);
        if (CollectionUtils.isNotEmpty(schoolDepts)) {
            schoolDepts.stream().forEach(e -> {
                SchoolDeptVo schoolDeptVo = new SchoolDeptVo();
                BeanUtils.copyProperties(e, schoolDeptVo);
                schoolDeptVos.add(schoolDeptVo);
            });
        }
        return schoolDeptVos;
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
}
