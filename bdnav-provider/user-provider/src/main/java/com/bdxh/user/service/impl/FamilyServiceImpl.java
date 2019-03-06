package com.bdxh.user.service.impl;

import com.bdxh.common.utils.BeanMapUtils;
import com.bdxh.common.web.support.BaseService;
import com.bdxh.user.dto.FamilyDto;
import com.bdxh.user.dto.FamilyQueryDto;
import com.bdxh.user.entity.Family;
import com.bdxh.user.entity.FamilyStudent;
import com.bdxh.user.persistence.FamilyMapper;
import com.bdxh.user.service.FamilyService;
import com.bdxh.user.service.FamilyStudentService;
import com.bdxh.user.vo.FamilyVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.util.List;

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
private FamilyStudentService familyStudentService;

    @Override
    public PageInfo<Family> getFamilyList(FamilyQueryDto familyQueryDto) {
        PageHelper.startPage(familyQueryDto.getPageNum(), familyQueryDto.getPageSize());
        Family family = BeanMapUtils.map(familyQueryDto, Family.class);
        List<Family> listFamily = familyMapper.select(family);
        PageInfo<Family> pageInfoFamily = new PageInfo<Family>(listFamily);
        return pageInfoFamily;
    }
    @Override
    @Transactional
    public void deleteFamilyInfo(String id) {
            familyMapper.deleteByPrimaryKey(Long.parseLong(id));
            FamilyStudent familyStudent=new FamilyStudent();
            familyStudent.setFamilyId(Long.parseLong(id));
            familyStudentService.delete(familyStudent);
    }

    @Override
    @Transactional
    public void deleteBatchesFamilyInfo(String id[]) {
        for (int i = 0; i < id.length; i++) {
            familyMapper.deleteByPrimaryKey(Long.parseLong(id[i]));
            FamilyStudent familyStudent=new FamilyStudent();
            familyStudent.setFamilyId(Long.parseLong(id[i]));
            familyStudentService.delete(familyStudent);
        }
    }

    @Override
    public FamilyVo selectBysCodeAndCard(Long id) {
        Family family=familyMapper.selectByPrimaryKey(id);
        FamilyVo familyVo=familyMapper.selectBysCodeAndCard(family.getSchoolCode(),family.getCardNumber());
        return familyVo;
    }
}
