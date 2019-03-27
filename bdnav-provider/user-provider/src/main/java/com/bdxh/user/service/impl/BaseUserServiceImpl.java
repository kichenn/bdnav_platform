package com.bdxh.user.service.impl;

import com.bdxh.common.support.BaseService;
import com.bdxh.user.dto.BaseUserQueryDto;
import com.bdxh.user.dto.UpdateBaseUserDto;
import com.bdxh.user.entity.BaseUser;
import com.bdxh.user.persistence.BaseUserMapper;
import com.bdxh.user.persistence.FamilyMapper;
import com.bdxh.user.persistence.StudentMapper;
import com.bdxh.user.persistence.TeacherMapper;
import com.bdxh.user.service.BaseUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    @Override
    public List<BaseUser> queryBaseUserInfo(BaseUserQueryDto baseUserQueryDto) {
        return baseUserMapper.queryBaseUserInfo(baseUserQueryDto);
    }

    @Override
    public void updateBaseUserInfo(UpdateBaseUserDto updateBaseUserDto) {
        baseUserMapper.updateBaseUserInfo(updateBaseUserDto);
    }

    @Override
    public void deleteBaseUserInfo(String schoolCode, String cadNumber) {
        baseUserMapper.deleteBaseUserInfo(schoolCode,cadNumber);
    }

    @Override
    public BaseUser queryBaseUserBySchoolCodeAndCardNumber(String schoolCode, String cadNumber) {
        return baseUserMapper.queryBaseUserBySchoolCodeAndCardNumber(schoolCode,cadNumber);
    }
}
