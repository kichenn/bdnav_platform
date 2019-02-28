package com.bdxh.school.service.impl;

import com.bdxh.common.web.support.BaseService;
import com.bdxh.school.dto.SchoolClassDto;
import com.bdxh.school.dto.SchoolClassModifyDto;
import com.bdxh.school.entity.SchoolClass;
import com.bdxh.school.persistence.SchoolClassMapper;
import com.bdxh.school.service.SchoolClassService;
import com.bdxh.school.helper.utils.LongUtils;
import com.bdxh.school.vo.SchoolClassVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 学校院系关系Service
 * @Author: Kang
 * @Date: 2019/2/26 17:58
 */
@Service
@Slf4j
public class SchoolClassServiceImpl extends BaseService<SchoolClass> implements SchoolClassService {

    @Autowired
    private SchoolClassMapper schoolClassMapper;

    //增加院校关系
    @Override
    public Boolean addSchoolClass(SchoolClassDto schoolClassDto) {
        SchoolClass schoolClass = new SchoolClass();
        BeanUtils.copyProperties(schoolClassDto, schoolClass);
        return schoolClassMapper.insertSelective(schoolClass) > 0;
    }

    //修改院校关系
    @Override
    public Boolean modifySchoolClass(SchoolClassModifyDto schoolClassDto) {
        SchoolClass schoolClass = new SchoolClass();
        BeanUtils.copyProperties(schoolClassDto, schoolClass);
        return schoolClassMapper.updateByPrimaryKeySelective(schoolClass) > 0;
    }

    //id删除院校关系
    @Override
    public Boolean delSchoolClassById(Long id) {
        return schoolClassMapper.deleteByPrimaryKey(id) > 0;
    }

    //id批量删除院校关系
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean batchDelSchoolClassInIds(List<Long> ids) {
        return schoolClassMapper.batchDelSchoolClassInIds(ids) > 0;
    }

    //删除院校底下信息
    @Override
    public Boolean delSchoolClassBySchoolId(Long schoolId) {
        return schoolClassMapper.delSchoolClassBySchoolId(schoolId) > 0;
    }

    //递归查询关系节点
    @Override
    public List<SchoolClassVo> findSchoolClassRelation(SchoolClassVo schoolClassVo) {
        List<SchoolClassVo> schoolClassVos = new ArrayList<>();
        if (LongUtils.isNotEmpty(schoolClassVo.getId())) {
            schoolClassVos.addAll(findSchoolClassByParentId(schoolClassVo.getId()));
            if (CollectionUtils.isNotEmpty(schoolClassVos)) {
                schoolClassVos.stream().forEach(e -> {
                    if (LongUtils.isNotEmpty(e.getId())) {
                        e.setSchoolClassVos(findSchoolClassRelation(e));
                    }
                });
            }
        }
        return schoolClassVos;
    }

    //学校id查询等级节点列表（一级节点为父节点）
    @Override
    public List<SchoolClass> findSchoolParentClassBySchoolId(Long schoolId, Byte level) {
        SchoolClass schoolClass = new SchoolClass();
        schoolClass.setLevel(level);
        schoolClass.setSchoolId(schoolId);
        return schoolClassMapper.select(schoolClass);
    }

    //关系id查询等级信息
    @Override
    public List<SchoolClassVo> findSchoolClassByParentId(Long parentId) {
        List<SchoolClassVo> schoolClassVos = new ArrayList<>();
        List<SchoolClass> schoolClasses = schoolClassMapper.findSchoolClassByParentId(parentId);
        if (CollectionUtils.isNotEmpty(schoolClasses)) {
            schoolClasses.stream().forEach(e -> {
                SchoolClassVo schoolClassVo = new SchoolClassVo();
                BeanUtils.copyProperties(e, schoolClassVo);
                schoolClassVos.add(schoolClassVo);
            });
        }
        return schoolClassVos;
    }

    //id查询信息
    @Override
    public SchoolClass findSchoolClassById(Long id) {
        return schoolClassMapper.selectByPrimaryKey(id);
    }

    //查询所有信息
    @Override
    public List<SchoolClass> findSchoolClassAll() {
        return schoolClassMapper.selectAll();
    }

}
