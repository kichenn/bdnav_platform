package com.bdxh.user.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.bdxh.common.helper.weixiao.authentication.AuthenticationUtils;
import com.bdxh.common.helper.weixiao.authentication.constant.AuthenticationConstant;
import com.bdxh.common.helper.weixiao.authentication.request.SynUserInfoRequest;
import com.bdxh.common.utils.BeanMapUtils;
import com.bdxh.common.support.BaseService;
import com.bdxh.common.utils.SnowflakeIdWorker;
import com.bdxh.user.dto.AddFamilyDto;
import com.bdxh.user.dto.FamilyQueryDto;
import com.bdxh.user.dto.UpdateFamilyDto;
import com.bdxh.user.entity.BaseUser;
import com.bdxh.user.entity.Family;
import com.bdxh.user.entity.Student;
import com.bdxh.user.persistence.BaseUserMapper;
import com.bdxh.user.persistence.FamilyMapper;
import com.bdxh.user.persistence.FamilyStudentMapper;
import com.bdxh.user.service.FamilyService;
import com.bdxh.user.vo.FamilyStudentVo;
import com.bdxh.user.vo.FamilyVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
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
    public void updateFamily(UpdateFamilyDto updateFamilyDto)  {
        try {
            Family family = BeanMapUtils.map(updateFamilyDto, Family.class);
            familyMapper.updateFamilyInfo(family);
            BaseUser updateBaseUserDto = BeanMapUtils.map(updateFamilyDto, BaseUser.class);
            baseUserMapper.updateBaseUserInfo(updateBaseUserDto);
            //修改时判断用户是否已经激活
            if(updateFamilyDto.getActivate().equals(Byte.parseByte("2"))){
                SynUserInfoRequest synUserInfoRequest=new SynUserInfoRequest();
                synUserInfoRequest.setName(updateFamilyDto.getName());
                synUserInfoRequest.setSchool_code(/*updateFamilyDto.getSchoolCode()*/"1044695883");
                synUserInfoRequest.setCard_number(updateFamilyDto.getCardNumber());
                synUserInfoRequest.setIdentity_type(AuthenticationConstant.FAMILY);
                synUserInfoRequest.setIdentity_title(AuthenticationConstant.FAMILY);
                synUserInfoRequest.setHead_image(updateFamilyDto.getImage());
                synUserInfoRequest.setGender(updateFamilyDto.getGender()==1?"男":"女");
                if(updateFamilyDto.getSchoolType()>=Byte.parseByte("4")){
                    synUserInfoRequest.setCollege(updateFamilyDto.getSchoolName());
                }
                synUserInfoRequest.setOrganization(updateFamilyDto.getSchoolName());
                synUserInfoRequest.setTelephone(updateFamilyDto.getPhone());
                synUserInfoRequest.setCard_type("1");
                synUserInfoRequest.setId_card(updateFamilyDto.getIdcard());
                synUserInfoRequest.setEmail(updateFamilyDto.getEmail());
                synUserInfoRequest.setQq(updateFamilyDto.getQqNumber());
                synUserInfoRequest.setNation(updateFamilyDto.getNationName());
                synUserInfoRequest.setAddress(updateFamilyDto.getAdress());
                synUserInfoRequest.setPhysical_card_number(updateFamilyDto.getPhysicalNumber());
                synUserInfoRequest.setPhysical_chip_number(updateFamilyDto.getPhysicalChipNumber());
                String result=AuthenticationUtils.synUserInfo(synUserInfoRequest,updateFamilyDto.getAppKey(),updateFamilyDto.getAppSecret());
                JSONObject jsonObject= JSONObject.parseObject(result);
                if(!jsonObject.get("errcode").equals(0)){
                    throw new Exception("家长信息同步失败,返回的错误码"+jsonObject.get("errcode")+"，同步家长卡号="+updateFamilyDto.getCardNumber()+"学校名称="+updateFamilyDto.getSchoolName());
                }
            }
        }catch (Exception e){
                e.printStackTrace();
                log.info("更新家长信息失败，错误信息="+e.getMessage());
        }
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
    @Transactional
    public void batchSaveFamilyInfo(List<AddFamilyDto> addFamilyDtoList) {
        List<Family> familyList = BeanMapUtils.mapList(addFamilyDtoList, Family.class);
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
