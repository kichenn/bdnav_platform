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

import java.util.ArrayList;
import java.util.HashMap;
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
            List<Map<String,String>>list =new ArrayList<>();
            for (int i = 0; i < cardNumbers.length; i++) {
                Map<String,String> map=new HashMap<>();
                map.put("cardNumber",cardNumbers[i]);
                map.put("schoolCode",schoolCodes[i]);
                list.add(map);
            }
            familyMapper.batchRemoveFamilyInfo(list);
            familyStudentMapper.batchRemoveFamilyStudentInfo(list);
            baseUserMapper.batchRemoveBaseUserInfo(list);
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
        Family family = BeanMapUtils.map(updateFamilyDto, Family.class);
        familyMapper.updateFamilyInfo(family);
        BaseUser updateBaseUserDto = BeanMapUtils.map(updateFamilyDto, BaseUser.class);
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

    @Override
    public void batchSaveFamilyInfo(List<Family> familyList) {
        List<BaseUser> baseUserList = BeanMapUtils.mapList(familyList, BaseUser.class);
        for (int i = 0; i < baseUserList.size(); i++) {
            familyList.get(i).setId(snowflakeIdWorker.nextId());
            baseUserList.get(i).setUserType(3);
            baseUserList.get(i).setUserId(familyList.get(i).getId());
            baseUserList.get(i).setId(snowflakeIdWorker.nextId());
        }
        familyMapper.batchSaveFamilyInfo(familyList);
        baseUserMapper.batchSaveBaseUserInfo(baseUserList);

    }

    @Override
    public List<String> queryFamilyCardNumberBySchoolCode(String schoolCode) {
        return familyMapper.queryFamilyCardNumberBySchoolCode(schoolCode);
    }
}
