package com.bdxh.user.service.impl;

import com.bdxh.common.utils.BeanMapUtils;
import com.bdxh.common.web.support.BaseService;
import com.bdxh.user.dto.FamilyQueryDto;
import com.bdxh.user.dto.UpdateFamilyDto;
import com.bdxh.user.entity.Family;
import com.bdxh.user.persistence.FamilyMapper;
import com.bdxh.user.persistence.FamilyStudentMapper;
import com.bdxh.user.service.FamilyService;
import com.bdxh.user.vo.FamilyStudentVo;
import com.bdxh.user.vo.FamilyVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
private FamilyStudentMapper familyStudentMapper;

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
    public void deleteFamilyInfo(String scoolCode,String cardNumber) {
            familyMapper.removeFamilyInfo(scoolCode,cardNumber);
             familyStudentMapper.familyRemoveFamilyStudent(scoolCode,cardNumber,null);
    }

    @Override
    @Transactional
    public void deleteBatchesFamilyInfo(String schoolCode,String cardNumber) {
             String[] schoolCodes=schoolCode.split(",");
             String[] cardNumbers=cardNumber.split(",");
             if(schoolCodes.length==cardNumbers.length) {
                 for (int i = 0; i < cardNumbers.length; i++) {
                     familyMapper.removeFamilyInfo(schoolCodes[i], cardNumbers[i]);
                     familyStudentMapper.familyRemoveFamilyStudent(schoolCodes[i], cardNumbers[i],null);
                 }
             }
    }

    @Override
    public FamilyVo selectBysCodeAndCard(String schoolCode,String cardNumber) {
        FamilyVo familyVo=familyMapper.selectByCodeAndCard(schoolCode,cardNumber);
        List<FamilyStudentVo> familyStudentVos=familyStudentMapper.selectFamilyStudentInfo(schoolCode, cardNumber);
        if(familyStudentVos.size()>0){
            familyVo.setStudents(familyStudentVos);
        }
        return familyVo;
    }

    @Override
    public FamilyVo isNullFamily(String schoolCode, String cardNumber) {
        return familyMapper.selectByCodeAndCard(schoolCode, cardNumber);
    }

    @Override
    public void updateFamily(UpdateFamilyDto updateFamilyDto) {
        familyMapper.updateFamilyInfo(updateFamilyDto);
    }
}
