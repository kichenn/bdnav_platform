package com.bdxh.user.service.impl;

import com.bdxh.common.utils.BeanMapUtils;
import com.bdxh.common.utils.BeanToMapUtil;
import com.bdxh.common.web.support.BaseService;
import com.bdxh.common.web.support.IService;
import com.bdxh.user.dto.FamilyQueryDto;
import com.bdxh.user.entity.Family;
import com.bdxh.user.entity.FamilyStudent;
import com.bdxh.user.persistence.FamilyMapper;
import com.bdxh.user.persistence.FamilyStudentMapper;
import com.bdxh.user.service.FamilyService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @description: 家长信息service实现
 * @author: xuyuan
 * @create: 2019-02-26 10:41
 **/
@Service
@Slf4j
public class FamilyServiceImpl extends BaseService<Family> implements FamilyService {

@Autowired
private FamilyMapper familyMapper;

@Autowired
private FamilyStudentMapper familyStudentMapper;

    //分页查询家长单表用户信息
    @Override
    public PageInfo<Family> getFamilyList(FamilyQueryDto familyQueryDto) {
        Family family = BeanMapUtils.map(familyQueryDto, Family.class);
        PageHelper.startPage(familyQueryDto.getPageNum(), familyQueryDto.getPageSize());
        List<Family> listFamily = familyMapper.select(family);
        PageInfo<Family> pageInfoFamily = new PageInfo<Family>(listFamily);
        return pageInfoFamily;
    }


    //根据ID删除家长信息和家长学生绑定关系信息
    @Override
    @Transactional
    public void deleteFamilyInfo(String id) {
            familyMapper.deleteByPrimaryKey(id);
            FamilyStudent familyStudent=new FamilyStudent();
            familyStudent.setFamilyId(Long.parseLong(id));
            familyStudentMapper.delete(familyStudent);
    }


    //根据ID批量删除家长信息和家长学生绑定关系信息
    @Override
    @Transactional
    public void deleteBatchesFamilyInfo(String id[]) {
        for (int i = 0; i < id.length; i++) {
            familyMapper.deleteByPrimaryKey(id[i]);
            FamilyStudent familyStudent=new FamilyStudent();
            familyStudent.setFamilyId(Long.parseLong(id[i]));
            familyStudentMapper.delete(familyStudent);
        }
    }


}
