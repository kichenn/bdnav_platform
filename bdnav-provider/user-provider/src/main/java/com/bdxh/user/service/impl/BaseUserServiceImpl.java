package com.bdxh.user.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.bdxh.common.base.enums.BaseUserTypeEnum;
import com.bdxh.common.helper.weixiao.authentication.AuthenticationUtils;
import com.bdxh.common.helper.weixiao.authentication.constant.AuthenticationConstant;
import com.bdxh.common.helper.weixiao.authentication.request.SynUserInfoRequest;
import com.bdxh.common.support.BaseService;
import com.bdxh.common.utils.BeanMapUtils;
import com.bdxh.user.dto.ActivationBaseUserDto;
import com.bdxh.user.dto.BaseUserQueryDto;
import com.bdxh.user.dto.UpdateBaseUserDto;
import com.bdxh.user.entity.*;
import com.bdxh.user.persistence.*;
import com.bdxh.user.service.BaseUserService;
import com.bdxh.user.vo.BaseEchartsVo;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @description: 基础用户service实现
 * @author: xuyuan
 * @create: 2019-03-26 18:36
 **/
@Service
@Slf4j
public class BaseUserServiceImpl extends BaseService<BaseUser> implements BaseUserService {

    @Autowired
    private BaseUserMapper baseUserMapper;

    @Autowired
    private FamilyMapper familyMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private TeacherDeptMapper teacherDeptMapper;

    @Override
    public List<BaseUser> queryBaseUserInfo(BaseUserQueryDto baseUserQueryDto) {
        return baseUserMapper.queryBaseUserInfo(baseUserQueryDto);
    }

    @Override
    public void updateBaseUserInfo(UpdateBaseUserDto updateBaseUserDto) {
        BaseUser baseUser = BeanMapUtils.map(updateBaseUserDto, BaseUser.class);
        baseUserMapper.updateBaseUserInfo(baseUser);
    }

    @Override
    public void deleteBaseUserInfo(String schoolCode, String cadNumber) {
        baseUserMapper.deleteBaseUserInfo(schoolCode, cadNumber);
    }

    @Override
    public BaseUser queryBaseUserBySchoolCodeAndCardNumber(String schoolCode, String cadNumber) {
        return baseUserMapper.queryBaseUserBySchoolCodeAndCardNumber(schoolCode, cadNumber);
    }

    @Override
    public List<String> queryAllUserPhone() {
        return baseUserMapper.queryAllUserPhone();
    }

    @Override
    public Integer queryUserPhoneByPhone(BaseUserQueryDto baseUserQueryDto) {
        return baseUserMapper.queryUserPhoneByPhone(baseUserQueryDto);
    }

    @Override
    public BaseUser queryBaseUserInfoByPhone(String phone) {
        BaseUser baseUser = new BaseUser();
        baseUser.setPhone(phone);
        return baseUserMapper.selectOne(baseUser);
    }

    @Override
    public List<BaseUser> findAllBaseUserInfo() {
        return baseUserMapper.findAllBaseUserInfo();
    }

    @Override
    public void updateSchoolName(String schoolCode, String schoolName) {
        baseUserMapper.updateSchoolName(schoolCode, schoolName);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean baseUserActivation(ActivationBaseUserDto activationBaseUserDto) {

            log.info("=================入参："+activationBaseUserDto.toString());
            log.info("--state:{},---appkey:{},----secret:{}",activationBaseUserDto.getState(),activationBaseUserDto.getAppKey(),activationBaseUserDto.getAppSecret());
            //更具学校Code和cardNumber查出我们本地用户的基本信息
            BaseUser baseUser = baseUserMapper.queryBaseUserBySchoolCodeAndCardNumber(activationBaseUserDto.getSchoolCode(), activationBaseUserDto.getCardNumber());
            Preconditions.checkArgument(null != baseUser, "不存在当前用户信息");
            if (baseUser.getUserType() != 0) {
                //判断用户类型  用户类型 1 学生 2 老师 3 家长
                switch (baseUser.getUserType()) {
                    case 1: {
                        //同步类型为学生的
                        log.info("卡号为" + baseUser.getCardNumber() + "的学生激活");
                        Student student = BeanMapUtils.map(activationBaseUserDto, Student.class);
                        student.setActivate(Byte.parseByte("2"));
                        studentMapper.updateStudentActivation(student.getSchoolCode(), student.getCardNumber(), student.getActivate());
                        Student studentInfo = studentMapper.findStudentInfo(activationBaseUserDto.getSchoolCode(), activationBaseUserDto.getCardNumber());
                        SynUserInfoRequest synUserInfoRequest = new SynUserInfoRequest();
                        synUserInfoRequest.setSchool_code(studentInfo.getSchoolCode());
                        synUserInfoRequest.setCard_number(studentInfo.getCardNumber());
                        synUserInfoRequest.setName(studentInfo.getName());
                        studentInfo.setGender(Byte.parseByte(baseUser.getGender() + ""));
                        synUserInfoRequest.setGender(studentInfo.getGender() == 1 ? "男" : "女");
                        if (activationBaseUserDto.getSchoolType() >= Byte.parseByte("4")) {
                            synUserInfoRequest.setClass_name(studentInfo.getClassName());
                            synUserInfoRequest.setGrade(studentInfo.getGradeName());
                            synUserInfoRequest.setCollege(studentInfo.getCollegeName());
                            synUserInfoRequest.setProfession(studentInfo.getProfessionName());
                        } else {
                            synUserInfoRequest.setClass_name(studentInfo.getClassName());
                            synUserInfoRequest.setGrade(studentInfo.getGradeName());
                        }
                        synUserInfoRequest.setReal_name_verify(Byte.valueOf("0"));
                        synUserInfoRequest.setOrganization(studentInfo.getClassNames());
                        synUserInfoRequest.setTelephone(studentInfo.getPhone());
                        synUserInfoRequest.setCard_type("1");
                        synUserInfoRequest.setId_card(studentInfo.getIdcard());
                        synUserInfoRequest.setHead_image(studentInfo.getImage());
                        synUserInfoRequest.setIdentity_type(AuthenticationConstant.STUDENT);
                        synUserInfoRequest.setNation(studentInfo.getNationName());
                        synUserInfoRequest.setQq(studentInfo.getQqNumber());
                        synUserInfoRequest.setAddress(studentInfo.getAdress());
                        synUserInfoRequest.setEmail(studentInfo.getEmail());
                        synUserInfoRequest.setPhysical_card_number(studentInfo.getPhysicalNumber());
                        synUserInfoRequest.setPhysical_chip_number(studentInfo.getPhysicalChipNumber());
                        synUserInfoRequest.setDorm_number(studentInfo.getDormitoryAddress());
                        synUserInfoRequest.setCampus(studentInfo.getCampusName());
                        String result = AuthenticationUtils.authUserInfo(synUserInfoRequest, activationBaseUserDto.getAppKey(), activationBaseUserDto.getAppSecret(), activationBaseUserDto.getState());
                        JSONObject jsonObject = JSONObject.parseObject(result);
                        if (!jsonObject.get("errcode").equals(0)) {
                            log.info("激活失败,返回的错误信息" + jsonObject.get("errmsg") + "，同步学生卡号=" + baseUser.getCardNumber() + "学校名称=" + baseUser.getSchoolName());
                            Preconditions.checkArgument(false, "激活失败");
                            return false;
                        }
                        return true;
                    }
                    case 2: {
                        log.info("卡号为" + baseUser.getCardNumber() + "的老师激活");
                        Teacher teacher = BeanMapUtils.map(activationBaseUserDto, Teacher.class);
                        teacher.setActivate(Byte.parseByte("2"));
                        teacherMapper.updateTeacher(teacher);
                        teacher = teacherMapper.selectTeacherDetails(activationBaseUserDto.getSchoolCode(), activationBaseUserDto.getCardNumber());
                        TeacherDept teacherDept = teacherDeptMapper.findTeacherBySchoolCodeAndCardNumber(activationBaseUserDto.getSchoolCode(), activationBaseUserDto.getCardNumber());
                        SynUserInfoRequest synUserInfoRequest = new SynUserInfoRequest();
                        synUserInfoRequest.setSchool_code(teacher.getSchoolCode());
                        synUserInfoRequest.setCard_number(teacher.getCardNumber());
                        synUserInfoRequest.setName(teacher.getName());
                        teacher.setGender(Byte.parseByte(baseUser.getGender() + ""));
                        synUserInfoRequest.setGender(teacher.getGender() == 1 ? "男" : "女");
                        synUserInfoRequest.setReal_name_verify(Byte.valueOf("0"));
                        synUserInfoRequest.setCard_type("1");
                        synUserInfoRequest.setIdentity_type(AuthenticationConstant.TEACHER);
                        synUserInfoRequest.setOrganization(teacherDept.getDeptNames());
                        synUserInfoRequest.setTelephone(teacher.getPhone());
                        synUserInfoRequest.setId_card(teacher.getIdcard());
                        synUserInfoRequest.setHead_image(teacher.getImage());
                        synUserInfoRequest.setIdentity_title(teacher.getPosition());
                        synUserInfoRequest.setNation(teacher.getNationName());
                        synUserInfoRequest.setQq(teacher.getQqNumber());
                        synUserInfoRequest.setAddress(teacher.getAdress());
                        synUserInfoRequest.setEmail(teacher.getEmail());
                        synUserInfoRequest.setPhysical_card_number(teacher.getPhysicalNumber());
                        synUserInfoRequest.setPhysical_chip_number(teacher.getPhysicalChipNumber());
                        synUserInfoRequest.setDorm_number(teacher.getDormitoryAddress());
                        synUserInfoRequest.setCampus(teacher.getCampusName());
                        String result = AuthenticationUtils.authUserInfo(synUserInfoRequest, activationBaseUserDto.getAppKey(), activationBaseUserDto.getAppSecret(), activationBaseUserDto.getState());
                        JSONObject jsonObject = JSONObject.parseObject(result);
                        if (!jsonObject.get("errcode").equals(0)) {
                            log.info("激活失败,返回的错误信息" + jsonObject.get("errmsg") + "，同步学生卡号=" + baseUser.getCardNumber() + "学校名称=" + baseUser.getSchoolName());
                            Preconditions.checkArgument(false, "激活失败");
                            return false;
                        }
                        return true;
                    }
                    case 3: {
                        log.info("卡号为" + baseUser.getCardNumber() + "的家长激活");
                        Family family = BeanMapUtils.map(activationBaseUserDto, Family.class);
                        family.setActivate(Byte.parseByte("2"));
                        familyMapper.updateFamilyInfo(family);
                        family = familyMapper.findFamilyInfo(family.getSchoolCode(), family.getCardNumber());
                        SynUserInfoRequest synUserInfoRequest = new SynUserInfoRequest();
                        synUserInfoRequest.setName(family.getName());
                        synUserInfoRequest.setSchool_code(family.getSchoolCode());
                        synUserInfoRequest.setCard_number(family.getCardNumber());
                        synUserInfoRequest.setIdentity_type(AuthenticationConstant.FAMILY);
                        synUserInfoRequest.setIdentity_title(AuthenticationConstant.FAMILY);
                        synUserInfoRequest.setHead_image(family.getImage());
                        family.setGender(Byte.parseByte(baseUser.getGender() + ""));
                        synUserInfoRequest.setGender(family.getGender() == 1 ? "男" : "女");
                        if (activationBaseUserDto.getSchoolType() >= Byte.parseByte("4")) {
                            synUserInfoRequest.setCollege(family.getSchoolName());
                        }
                        synUserInfoRequest.setReal_name_verify(Byte.valueOf("0"));
                        synUserInfoRequest.setOrganization(family.getSchoolName());
                        synUserInfoRequest.setTelephone(family.getPhone());
                        synUserInfoRequest.setCard_type("1");
                        synUserInfoRequest.setId_card(family.getIdcard());
                        synUserInfoRequest.setEmail(family.getEmail());
                        synUserInfoRequest.setQq(family.getQqNumber());
                        synUserInfoRequest.setNation(family.getNationName());
                        synUserInfoRequest.setAddress(family.getAdress());
                        synUserInfoRequest.setPhysical_card_number(family.getPhysicalNumber());
                        synUserInfoRequest.setPhysical_chip_number(family.getPhysicalChipNumber());
                        String result = AuthenticationUtils.authUserInfo(synUserInfoRequest, activationBaseUserDto.getAppKey(), activationBaseUserDto.getAppSecret(), activationBaseUserDto.getState());
                        JSONObject jsonObject = JSONObject.parseObject(result);
                        if (!jsonObject.get("errcode").equals(0)) {
                           log.info("激活失败" + jsonObject.get("errmsg") + "，同步学生卡号=" + baseUser.getCardNumber() + "学校名称=" + baseUser.getSchoolName());
                           Preconditions.checkArgument(false, "激活失败");
                            return false;
                        }
                        return true;
                    }
                    default: {
                        Preconditions.checkArgument(false, "激活失败,不存在当前用户类型");
                        return false;
                    }
                }
            }
            return false;
    }

    @Override
    public List<String> findSchoolNumberBySchool(String schoolCode) {
        return baseUserMapper.findSchoolNumberBySchool(schoolCode);
    }

    /**
     * 查询学校用户的分类数量
     *
     * @param schoolCode 学校编码
     * @return
     * @author WanMing
     */
    @Override
    public List<BaseEchartsVo> querySchoolUserCategoryCount(String schoolCode) {
        //有schoolCode查各自学校的用户分类数   无schoolCode查大后台用户分类数据
        List<BaseEchartsVo> baseEchartsVos = baseUserMapper.querySchoolUserCategoryCount(schoolCode, null);
        Map<String, BaseEchartsVo> baseEchartsVoMap = baseEchartsVos.stream()
                .collect(Collectors.toMap(BaseEchartsVo::getName, Function.identity()));
        //通过用户枚举类型确保返回到前端时,三种类型都有数据
        BaseUserTypeEnum[] userTypeEnums = BaseUserTypeEnum.values();
        List<BaseEchartsVo> baseVos = new ArrayList<>(userTypeEnums.length);
        for (BaseUserTypeEnum userTypeEnum : userTypeEnums) {
            BaseEchartsVo baseEchartsVo = baseEchartsVoMap.get(userTypeEnum.getCode().toString());
            if (null == baseEchartsVo) {
                BaseEchartsVo echartsVo = new BaseEchartsVo();
                echartsVo.setName(userTypeEnum.getDesc());
                echartsVo.setValue(0L);
                baseVos.add(echartsVo);
            } else {
                baseEchartsVo.setName(userTypeEnum.getDesc());
                baseEchartsVo.setValue(baseEchartsVo.getValue());
                baseVos.add(baseEchartsVo);
            }
        }
        return baseVos;
    }
}
