package com.bdxh.user.service;

import com.bdxh.common.support.IService;
import com.bdxh.user.dto.BaseUserQueryDto;
import com.bdxh.user.dto.UpdateBaseUserDto;
import com.bdxh.user.entity.BaseUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @description:
 * @author: binzh
 * @create: 2019-03-27 09:54
 **/
public interface BaseUserService extends IService<BaseUser> {
    /**
     * 根据条件查询所有用户
     * @param baseUserQueryDto
     * @return
     */
    List<BaseUser> queryBaseUserInfo(BaseUserQueryDto baseUserQueryDto);

    /**
     * 修改用户数据
     * @param updateBaseUserDto
     * @return
     */
    void updateBaseUserInfo( UpdateBaseUserDto updateBaseUserDto);

    /**
     * 删除用户数据
     * @param schoolCode
     * @param cadNumber
     * @return
     */
    void deleteBaseUserInfo(String schoolCode,String cadNumber);

    /**
     * 查询单个用户信息
     * @param schoolCode
     * @param cadNumber
     * @return
     */
    BaseUser queryBaseUserBySchoolCodeAndCardNumber(String schoolCode,String cadNumber);

    /**
     * 查询所有用户手机号
     * @return
     */
    List<String> queryAllUserPhone();

    /**
     * 根据手机号查询有没有重复的手机号
     * @param baseUserQueryDto
     * @return
     */
    Integer queryUserPhoneByPhone(BaseUserQueryDto baseUserQueryDto);

    /**
     * 根据手机号查询学校基础用户信息
     * @param phone
     * @return
     */
    BaseUser queryBaseUserInfoByPhone(String phone);
}