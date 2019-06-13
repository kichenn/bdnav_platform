package com.bdxh.user.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.bdxh.common.helper.weixiao.authentication.AuthenticationUtils;
import com.bdxh.common.helper.weixiao.authentication.constant.AuthenticationConstant;
import com.bdxh.common.helper.weixiao.authentication.request.SynUserInfoRequest;
import com.bdxh.common.utils.BeanMapUtils;
import com.bdxh.common.support.BaseService;
import com.bdxh.common.utils.SnowflakeIdWorker;
import com.bdxh.user.configration.rocketmq.properties.RocketMqProducerProperties;
import com.bdxh.user.dto.AddFamilyDto;
import com.bdxh.user.dto.FamilyQueryDto;
import com.bdxh.user.dto.UpdateBaseUserDto;
import com.bdxh.user.dto.UpdateFamilyDto;
import com.bdxh.user.entity.BaseUser;
import com.bdxh.user.entity.BaseUserUnqiue;
import com.bdxh.user.entity.Family;
import com.bdxh.user.persistence.BaseUserMapper;
import com.bdxh.user.persistence.BaseUserUnqiueMapper;
import com.bdxh.user.persistence.FamilyMapper;
import com.bdxh.user.persistence.FamilyStudentMapper;
import com.bdxh.user.service.FamilyService;
import com.bdxh.user.vo.FamilyStudentVo;
import com.bdxh.user.vo.FamilyVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
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
    private BaseUserUnqiueMapper baseUserUnqiueMapper;

    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    @Autowired
    private DefaultMQProducer defaultMQProducer;

    @Autowired
    private RocketMqProducerProperties rocketMqProducerProperties;

    @Override
    public PageInfo<Family> getFamilyList(FamilyQueryDto familyQueryDto) {
        PageHelper.startPage(familyQueryDto.getPageNum(), familyQueryDto.getPageSize());
        List<Family> listFamily = familyMapper.selectAllFamilyInfo(familyQueryDto);
        PageInfo<Family> pageInfoFamily = new PageInfo<Family>(listFamily);
        return pageInfoFamily;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteFamilyInfo(String scoolCode, String cardNumber) {
        BaseUser baseUser=baseUserMapper.queryBaseUserBySchoolCodeAndCardNumber(scoolCode,cardNumber);
        baseUserUnqiueMapper.deleteByPrimaryKey(baseUser.getId());
        familyMapper.removeFamilyInfo(scoolCode, cardNumber);
        familyStudentMapper.familyRemoveFamilyStudent(scoolCode, cardNumber, null);
        baseUserMapper.deleteBaseUserInfo(scoolCode, cardNumber);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatchesFamilyInfo(String schoolCode, String cardNumber) {
        String[] schoolCodes = schoolCode.split(",");
        String[] cardNumbers = cardNumber.split(",");
        List<Map<String, String>> list = new ArrayList<>();
        for (int i = 0; i < cardNumbers.length; i++) {
            Map<String, String> map = new HashMap<>();
            map.put("cardNumber", cardNumbers[i]);
            map.put("schoolCode", schoolCodes[i]);
            list.add(map);
        }
        familyMapper.batchRemoveFamilyInfo(list);
        familyStudentMapper.batchRemoveFamilyStudentInfo(list);
        baseUserMapper.batchRemoveBaseUserInfo(list);


    }


    @Override
    public FamilyVo selectBysCodeAndCard(String schoolCode, String cardNumber) {
        FamilyVo familyVo = familyMapper.selectByCodeAndCard(schoolCode, cardNumber);
        List<FamilyStudentVo> familyStudentVos = familyStudentMapper.selectFamilyStudentInfo(schoolCode, cardNumber);
        if (familyStudentVos.size() > 0) {
            familyVo.setStudents(familyStudentVos);
        }
        return familyVo;
    }

    @Override
    public FamilyVo isNullFamily(String schoolCode, String cardNumber) {
        return familyMapper.selectByCodeAndCard(schoolCode, cardNumber);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateFamily(UpdateFamilyDto updateFamilyDto) {

            //查出修改之前的基础用户信息
            BaseUser baseUser=baseUserMapper.queryBaseUserBySchoolCodeAndCardNumber(updateFamilyDto.getSchoolCode(),
                    updateFamilyDto.getCardNumber());
            //如果改了手机号码就进行修改
            if(!baseUser.getPhone().equals(updateFamilyDto.getPhone())){
                try {
                    baseUserUnqiueMapper.updateUserPhoneByUserId(baseUser.getId(),updateFamilyDto.getPhone(),updateFamilyDto.getSchoolCode());
                }catch (Exception e){
                    String message=e.getMessage();
                    if (e instanceof DuplicateKeyException) {
                        Preconditions.checkArgument(false,"当前手机号码已重复请重新填写");
                    }
                }
            }
        try {
            //修改家长信息
            Family family = BeanMapUtils.map(updateFamilyDto, Family.class);
            familyMapper.updateFamilyInfo(family);
            //修改基础用户信息
            BaseUser updateBaseUserDto = BeanMapUtils.map(updateFamilyDto, BaseUser.class);
            baseUserMapper.updateBaseUserInfo(updateBaseUserDto);

            //修改时判断用户是否已经激活
            if (updateFamilyDto.getActivate().equals(Byte.parseByte("2"))) {
                SynUserInfoRequest synUserInfoRequest = new SynUserInfoRequest();
                synUserInfoRequest.setName(updateFamilyDto.getName());
                synUserInfoRequest.setSchool_code(updateFamilyDto.getSchoolCode());
                synUserInfoRequest.setCard_number(updateFamilyDto.getCardNumber());
                synUserInfoRequest.setIdentity_type(AuthenticationConstant.FAMILY);
                synUserInfoRequest.setIdentity_title(AuthenticationConstant.FAMILY);
                synUserInfoRequest.setHead_image(updateFamilyDto.getImage());
                synUserInfoRequest.setGender(updateFamilyDto.getGender() == 1 ? "男" : "女");
                if (updateFamilyDto.getSchoolType() >= Byte.parseByte("4")) {
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
                String result = AuthenticationUtils.synUserInfo(synUserInfoRequest, updateFamilyDto.getAppKey(), updateFamilyDto.getAppSecret());
                JSONObject jsonObject = JSONObject.parseObject(result);
                if (!jsonObject.get("errcode").equals(0)) {
                    throw new Exception("家长信息同步失败,返回的错误码" + jsonObject.get("errcode") + "，同步家长卡号=" + updateFamilyDto.getCardNumber() + "学校名称=" + updateFamilyDto.getSchoolName());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.info("更新家长信息失败，错误信息=" + e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveFamily(Family family) {
        BaseUser baseUser = BeanMapUtils.map(family, BaseUser.class);
        baseUser.setId(snowflakeIdWorker.nextId());
        try {
            baseUserUnqiueMapper.insertUserPhone(baseUser.getId(),baseUser.getPhone(),baseUser.getSchoolCode());
        }catch (Exception e){
            String message=e.getMessage();
            if (e instanceof DuplicateKeyException) {
                Preconditions.checkArgument(false,"当前手机号码已重复请重新填写");
            }
        }
        baseUser.setUserType(3);
        baseUser.setUserId(family.getId());
        baseUserMapper.insert(baseUser);
        family.setId(snowflakeIdWorker.nextId());
        family.setActivate(Byte.valueOf("1"));
        familyMapper.insert(family);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchSaveFamilyInfo(List<AddFamilyDto> addFamilyDtoList) {
        List<Family> familyList = BeanMapUtils.mapList(addFamilyDtoList, Family.class);
        List<BaseUser> baseUserList = BeanMapUtils.mapList(familyList, BaseUser.class);
        for (int i = 0; i < baseUserList.size(); i++) {
            familyList.get(i).setId(snowflakeIdWorker.nextId());
            baseUserList.get(i).setUserType(3);
            baseUserList.get(i).setUserId(familyList.get(i).getId());
            baseUserList.get(i).setId(snowflakeIdWorker.nextId());
        }
        List<BaseUserUnqiue> baseUserUnqiueList=BeanMapUtils.mapList(baseUserList,BaseUserUnqiue.class);
        baseUserUnqiueMapper.batchSaveBaseUserPhone(baseUserUnqiueList);
        familyMapper.batchSaveFamilyInfo(familyList);
        baseUserMapper.batchSaveBaseUserInfo(baseUserList);
    }

    @Override
    public List<String> queryFamilyCardNumberBySchoolCode(String schoolCode) {
        return familyMapper.queryFamilyCardNumberBySchoolCode(schoolCode);
    }

    @Override
    public void updateSchoolName(String schoolCode, String schoolName) {
        familyMapper.updateSchoolName(schoolCode, schoolName);
    }


}
