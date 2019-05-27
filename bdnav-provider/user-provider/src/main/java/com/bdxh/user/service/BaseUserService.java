package com.bdxh.user.service;

import com.bdxh.common.support.IService;
import com.bdxh.user.dto.ActivationBaseUserDto;
import com.bdxh.user.dto.BaseUserQueryDto;
import com.bdxh.user.dto.UpdateBaseUserDto;
import com.bdxh.user.entity.BaseUser;
import com.bdxh.user.vo.BaseEchartsVo;
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
     * 查询所有不是家长的用户
     * @return
     */
    List<BaseUser> findAllBaseUserInfo();
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

    /**
     * 修改学校名字
     * @param schoolCode
     * @param schoolName
     */
    void updateSchoolName(String schoolCode,String schoolName);

    /**
     * 校方页面认证激活用户并同步信息
     * @param activationBaseUserDto
     */
    Boolean baseUserActivation(ActivationBaseUserDto activationBaseUserDto);


    /**
     * 根据学校Codec查询所有卡号
     * @param schoolCode
     */
    List<String> findSchoolNumberBySchool(String schoolCode);


    /**
     * 查询学校用户的分类数量
     * @param schoolCode
     * @return
     */
    List<BaseEchartsVo> querySchoolUserCategoryCount(String schoolCode);
}