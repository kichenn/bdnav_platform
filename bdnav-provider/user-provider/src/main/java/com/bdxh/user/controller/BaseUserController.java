package com.bdxh.user.controller;

import com.bdxh.common.helper.ali.sms.constant.AliyunSmsConstants;
import com.bdxh.common.helper.ali.sms.enums.SmsTempletEnum;
import com.bdxh.common.helper.ali.sms.utils.SmsUtil;
import com.bdxh.common.utils.RandomUtil;
import com.bdxh.common.utils.ValidatorUtil;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.user.configration.redis.RedisUtil;
import com.bdxh.user.dto.ActivationBaseUserDto;
import com.bdxh.user.dto.BaseUserQueryDto;
import com.bdxh.user.entity.BaseUser;
import com.bdxh.user.entity.BaseUserUnqiue;
import com.bdxh.user.service.BaseUserService;
import com.bdxh.user.service.BaseUserUnqiueService;
import com.bdxh.user.vo.FamilyVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @description:
 * @author: binzh
 * @create: 2019-04-25 11:21
 **/
@Api(value ="学校用户信息管理接口API", tags = "学校用户信息管理接口API")
@RestController
@RequestMapping("/baseUser")
@Validated
@Slf4j
public class BaseUserController {
    @Autowired
    private BaseUserService baseUserService;

    @Autowired
    private BaseUserUnqiueService baseUserUnqiueService;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 查询所有用户手机号
     * @return
     */
    @ApiOperation(value="查询所有用户手机号")
    @RequestMapping(value ="/queryAllUserPhone",method = RequestMethod.GET)
    public Object queryAllUserPhone(@RequestBody BaseUserUnqiue baseUserUnqiue) {
        try {
            List<String> list=baseUserUnqiueService.queryAllUserPhone(baseUserUnqiue);
            return WrapMapper.ok(list) ;
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }
    /**
     * 根据手机号查询所有用户手机号是否存在
     * @return
     */
    @ApiOperation(value="根据手机号查询所有用户手机号是否存在")
    @RequestMapping(value ="/queryUserPhoneByPhone",method = RequestMethod.POST)
    public Object queryUserPhoneByPhone(@RequestBody  BaseUserQueryDto baseUserQueryDto) {
        try {
            Integer phoneCount=baseUserUnqiueService.queryUserPhone(baseUserQueryDto.getPhone(),baseUserQueryDto.getSchoolCode());
            return WrapMapper.ok(phoneCount) ;
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }
    /**
     * 根据手机号查询学校基础用户信息
     * @return
     */
    @ApiOperation(value="根据手机号查询学校基础用户信息")
    @RequestMapping(value ="/queryBaseUserInfoByPhone",method = RequestMethod.GET)
    public Object queryBaseUserInfoByPhone(@RequestParam("phone")String phone) {
        try {
            BaseUser baseUser=baseUserService.queryBaseUserInfoByPhone(phone);
            return WrapMapper.ok(baseUser) ;
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }
    /**
     * 查询学校所有除了家长的用户
     */
    @RequestMapping(value ="/findAllBaseUserInfo",method = RequestMethod.POST)
    @ResponseBody
   public Object  findAllBaseUserInfo(){
        try {
            return WrapMapper.ok(baseUserService.findAllBaseUserInfo()) ;
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 根据学校码和卡号查询用户信息
     */
    @RequestMapping(value ="/queryBaseUserBySchoolCodeAndCardNumber",method = RequestMethod.POST)
    public Object  queryBaseUserBySchoolCodeAndCardNumber(@RequestParam("schoolCode") String schoolCode,
                                                          @RequestParam("cardNumber") String cardNumber){
        try {
            return WrapMapper.ok(baseUserService.queryBaseUserBySchoolCodeAndCardNumber(schoolCode,cardNumber)) ;
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }
    /**
     * 校方激活同步用户接口
     */
    @RequestMapping(value ="/baseUserActivation",method = RequestMethod.POST)
    public Object  baseUserActivation(@RequestBody ActivationBaseUserDto activationBaseUserDto){
        try {
            return WrapMapper.ok(baseUserService.baseUserActivation(activationBaseUserDto));
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }
    /**
     * 判断卡号是否重复
     */
    @RequestMapping(value ="/findSchoolNumberBySchool",method = RequestMethod.POST)
    public Object  findSchoolNumberBySchool(@RequestParam("schoolCode") String schoolCode){
        try {
            return WrapMapper.ok(baseUserService.findSchoolNumberBySchool(schoolCode)) ;
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    @ApiOperation(value = "微校平台----激活手机获取短信验证码")
    @RequestMapping(value = "/getPhoneCode",method = RequestMethod.POST)
    public Object getPhoneCode(@RequestParam(name="phone")@NotNull(message = "手机号码不能为空") String phone){
        if (!ValidatorUtil.isMobile(phone)) {
            return WrapMapper.error("请输入正确的手机号");
        }
        //生成随机数
        String code = RandomUtil.createNumberCode(4);
        redisUtil.setWithExpireTime(AliyunSmsConstants.CodeConstants.CAPTCHA_PREFIX +phone,code,AliyunSmsConstants.CodeConstants.CAPTCHA_TIME);
        SmsUtil.sendMsgHelper(SmsTempletEnum.TEMPLATE_VERIFICATION, phone, code + ",:校园卡激活");
        return WrapMapper.ok(Boolean.TRUE);
    }

    /**
     * 展示学校用户分类数据量的信息
     * @return 学校用户分类数据量
     */
    @RequestMapping(value = "/querySchoolUserCategoryCount", method = RequestMethod.GET)
    @ApiOperation(value = "查询学校用户分类数量")
    public Object querySchoolUserCategoryCount(@RequestParam(value = "schoolCode", required = false) String schoolCode) {
        return WrapMapper.ok(baseUserService.querySchoolUserCategoryCount(schoolCode));
    }
}