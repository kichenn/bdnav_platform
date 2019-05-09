package com.bdxh.weixiao.controller.user;

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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
}