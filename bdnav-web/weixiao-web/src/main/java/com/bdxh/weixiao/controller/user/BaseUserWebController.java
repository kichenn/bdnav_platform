package com.bdxh.weixiao.controller.user;


import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.school.entity.School;
import com.bdxh.school.feign.SchoolControllerClient;
import com.bdxh.user.dto.*;
import com.bdxh.user.feign.BaseUserControllerClient;
import com.bdxh.weixiao.configration.redis.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

/**
 * @description: 学校用户(学生 ， 家长 ， 老师)控制器
 * @author: binzh
 * @create: 2019-04-26 14:06
 **/
@Slf4j
@RestController
@Api(value = "微校平台----微校学校用户(学生 ， 家长 ， 老师)控制器", tags = "微校平台----微校学校用户(学生 ， 家长 ， 老师)控制器")
@Validated
public class BaseUserWebController {
    @Autowired
    private BaseUserControllerClient baseUserControllerClient;

    @Autowired
    private SchoolControllerClient schoolControllerClient;

    @Autowired
    private RedisUtil redisUtil;
    /**
     * 用户激活接口
     *
     * @param
     * @return
     */
    @ApiOperation(value = "微校平台----用户激活接口")
    @RequestMapping(value = "/authenticationWeixiao/activationBaseUser", method = RequestMethod.POST)
    public Object activationBaseUser(@RequestBody ActivationBaseUserDto activationBaseUserDto) {
           //判断手机验证码是否正确
/*            String saveCode=redisUtil.get(AliyunSmsConstants.CodeConstants.CAPTCHA_PREFIX +activationBaseUserDto.getPhone());
            if(!activationBaseUserDto.getCode().equals(saveCode)){
                return WrapMapper.error("手机验证码错误");
            }*/
            log.info("------------"+activationBaseUserDto.toString());
            School school = schoolControllerClient.findSchoolBySchoolCode(activationBaseUserDto.getSchoolCode()).getResult();
            activationBaseUserDto.setAppKey(school.getSchoolKey());
            activationBaseUserDto.setAppSecret(school.getSchoolSecret());
            activationBaseUserDto.setSchoolType(school.getSchoolType());
            log.info("============"+activationBaseUserDto.toString());
               Wrapper wrapper= baseUserControllerClient.baseUserActivation(activationBaseUserDto);
            log.info("==========返回给前端页面的数据：   {}",wrapper);
            return wrapper;
    }

    /**
     * 激活校园卡时手机获取短信验证码
     * @param phone
     * @return
     */
    @ApiOperation(value = "微校平台----激活校园卡时手机获取短信验证码")
    @RequestMapping(value = "/authenticationWeixiao/getPhoneCode",method = RequestMethod.POST)
    public Object getPhoneCode(@RequestParam(name="phone")@NotNull(message = "手机号码不能为空") String phone){
        return baseUserControllerClient.getPhoneCode(phone);
    }
}