package com.bdxh.weixiao.controller.user;

import com.bdxh.common.helper.ali.sms.constant.AliyunSmsConstants;
import com.bdxh.common.utils.BeanMapUtils;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.school.entity.School;
import com.bdxh.school.feign.SchoolControllerClient;
import com.bdxh.school.vo.SchoolInfoVo;
import com.bdxh.user.dto.*;
import com.bdxh.user.entity.BaseUser;
import com.bdxh.user.feign.BaseUserControllerClient;
import com.bdxh.user.feign.FamilyControllerClient;
import com.bdxh.user.feign.StudentControllerClient;
import com.bdxh.user.feign.TeacherControllerClient;
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
@RequestMapping(value = "/baseUser")
@RestController
@Api(value = "微校平台----学校用户(学生 ， 家长 ， 老师)控制器", tags = "微校平台----学校用户(学生 ， 家长 ， 老师)控制器")
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
    @ApiOperation(value = "用户激活接口")
    @RequestMapping(value = "/activationBaseUser", method = RequestMethod.POST)
    public Object activationBaseUser(@RequestBody ActivationBaseUserDto activationBaseUserDto) {
        //查询出激活用户所需要的第三方参数
        try {
           //判断手机验证码是否正确
            String saveCode=redisUtil.get(AliyunSmsConstants.CodeConstants.CAPTCHA_PREFIX +activationBaseUserDto.getPhone());
            if(!activationBaseUserDto.getCode().equals(saveCode)){
                return WrapMapper.error("手机验证码错误");
            }
            School school = schoolControllerClient.findSchoolBySchoolCode(activationBaseUserDto.getSchoolCode()).getResult();
            activationBaseUserDto.setAppKey(school.getAppKey());
            activationBaseUserDto.setAppSecret(school.getAppSecret());
            activationBaseUserDto.setSchoolType(school.getSchoolType());
            return WrapMapper.ok(baseUserControllerClient.baseUserActivation(activationBaseUserDto).getResult());
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error("false");
        }
    }

    @ApiOperation(value = "微校平台----激活校园卡时手机获取短信验证码")
    @RequestMapping(value = "/getPhoneCode",method = RequestMethod.POST)
    public Object getPhoneCode(@RequestParam(name="phone")@NotNull(message = "手机号码不能为空") String phone){
        return baseUserControllerClient.getPhoneCode(phone);
    }
}