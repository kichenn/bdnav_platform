package com.bdxh.user.service.impl;

import com.bdxh.common.utils.BeanMapUtils;
import com.bdxh.common.support.BaseService;
import com.bdxh.common.utils.SnowflakeIdWorker;
import com.bdxh.user.dto.FamilyQueryDto;
import com.bdxh.user.dto.UpdateBaseUserDto;
import com.bdxh.user.dto.UpdateFamilyDto;
import com.bdxh.user.entity.BaseUser;
import com.bdxh.user.entity.Family;
import com.bdxh.user.persistence.BaseUserMapper;
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
private BaseUserMapper baseUserMapper;

@Autowired
private FamilyStudentMapper familyStudentMapper;

@Autowired
private SnowflakeIdWorker snowflakeIdWorker;

    @Override
    public PageInfo<Family> getFamilyList(FamilyQueryDto familyQueryDto) {
        PageHelper.startPage(familyQueryDto.getPageNum(), familyQueryDto.getPageSize());
        List<Family> listFamily = familyMapper.selectAllFamilyInfo(familyQueryDto);
        PageInfo<Family> pageInfoFamily = new PageInfo<Family>(listFamily);
        return pageInfoFamily;
    }

    @Override
    @Transactional
    public void deleteFamilyInfo(String scoolCode,String cardNumber) {
            familyMapper.removeFamilyInfo(scoolCode,cardNumber);
             familyStudentMapper.familyRemoveFamilyStudent(scoolCode,cardNumber,null);
        baseUserMapper.deleteBaseUserInfo(scoolCode,cardNumber);
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
                     baseUserMapper.deleteBaseUserInfo(schoolCodes[i],cardNumbers[i]);
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
    @Transactional
    public void updateFamily(UpdateFamilyDto updateFamilyDto) {
        familyMapper.updateFamilyInfo(updateFamilyDto);
        UpdateBaseUserDto updateBaseUserDto = BeanMapUtils.map(updateFamilyDto, UpdateBaseUserDto.class);
        baseUserMapper.updateBaseUserInfo(updateBaseUserDto);
    }

    @Override
    @Transactional
    public void saveFamily(Family family) {
        family.setId(snowflakeIdWorker.nextId());
        family.setActivate(Byte.valueOf("1"));
        familyMapper.insert(family);
        BaseUser baseUser = BeanMapUtils.map(family, BaseUser.class);
        baseUser.setUserType(3);
        baseUser.setUserId(family.getId());
        baseUser.setId(snowflakeIdWorker.nextId());
        baseUserMapper.insert(baseUser);
    }
}
