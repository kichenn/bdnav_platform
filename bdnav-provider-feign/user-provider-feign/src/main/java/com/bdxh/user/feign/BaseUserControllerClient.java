package com.bdxh.user.feign;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.user.dto.ActivationBaseUserDto;
import com.bdxh.user.dto.BaseUserQueryDto;
import com.bdxh.user.entity.BaseUser;
import com.bdxh.user.entity.BaseUserUnqiue;
import com.bdxh.user.fallback.BaseUserControllerFallback;
import com.bdxh.user.vo.BaseEchartsVo;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;


/**
 * @description:
 * @author: binzh
 * @create: 2019-04-25 11:28
 **/
@Service
@FeignClient(value = "user-provider-cluster", fallback = BaseUserControllerFallback.class)
public interface BaseUserControllerClient {

    /**
     * 查询所有手机号信息
     *
     * @return
     */
    @RequestMapping(value = "/baseUser/queryAllUserPhone", method = RequestMethod.GET)
    @ResponseBody
    Wrapper<List<String>> queryAllUserPhone(@RequestBody  BaseUserUnqiue baseUserUnqiue);


    /**
     * 根据手机号查询所有用户手机号是否存在
     *
     * @return
     */
    @RequestMapping(value = "/baseUser/queryUserPhoneByPhone", method = RequestMethod.POST)
    @ResponseBody
    Wrapper queryUserPhoneByPhone(@RequestBody BaseUserQueryDto baseUserQueryDto);

    /**
     * 根据手机号查询学生信息
     *
     * @return BaseUser
     */
    @RequestMapping(value = "/baseUser/queryBaseUserInfoByPhone", method = RequestMethod.GET)
    @ResponseBody
    Wrapper<BaseUser> queryBaseUserInfoByPhone(@RequestParam("phone") String phone);

    /**
     * 查询学校所有除了家长的用户
     */
    @RequestMapping(value = "/baseUser/findAllBaseUserInfo", method = RequestMethod.POST)
    @ResponseBody
    Wrapper<List<BaseUser>> findAllBaseUserInfo();

    /**
     * 查询基础用户信息
     *
     * @param schoolCode
     * @param cardNumber
     * @return
     */
    @RequestMapping(value = "/baseUser/queryBaseUserBySchoolCodeAndCardNumber", method = RequestMethod.POST)
    @ResponseBody
    Wrapper<BaseUser> queryBaseUserBySchoolCodeAndCardNumber(@RequestParam("schoolCode") String schoolCode,
                                                             @RequestParam("cardNumber") String cardNumber);

    /**
     * 校方地址用户激活同步微校
     * @param activationBaseUserDto
     * @return
     */
    @RequestMapping(value = "/baseUser/baseUserActivation", method = RequestMethod.POST)
    @ResponseBody
    Wrapper<Boolean> baseUserActivation(@RequestBody ActivationBaseUserDto activationBaseUserDto);

    /**
     * 判断卡号是否重复查询一个学校所有卡号
     * @param schoolCode
     * @return
     */
    @ResponseBody
    @RequestMapping(value ="/baseUser/findSchoolNumberBySchool",method = RequestMethod.POST)
    Wrapper<List<String>>  findSchoolNumberBySchool(@RequestParam("schoolCode") String schoolCode);


    /**
     * 激活校园卡时手机号码获取验证码
     * @param phone
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/baseUser/getPhoneCode",method = RequestMethod.POST)
    Wrapper<Boolean> getPhoneCode(@RequestParam(name="phone")@NotNull(message = "手机号码不能为空") String phone);

    /**
     * 展示学校用户分类数据量的信息
     * @return 学校用户分类数据量
     */
    @RequestMapping(value = "/baseUser/querySchoolUserCategoryCount", method = RequestMethod.GET)
    @ResponseBody
    Wrapper<List<BaseEchartsVo>> querySchoolUserCategoryCount(@RequestParam(value = "schoolCode", required = false) String schoolCode);
}