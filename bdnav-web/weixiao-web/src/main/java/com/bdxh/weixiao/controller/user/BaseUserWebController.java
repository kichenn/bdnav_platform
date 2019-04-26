package com.bdxh.weixiao.controller.user;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.user.dto.BaseUserQueryDto;
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
@Api(value = "微校平台----学校用户(学生 ， 家长 ， 老师)控制器")
@Validated
public class BaseUserWebController {
    @Autowired
    private BaseUserControllerClient baseUserControllerClient;
    @Autowired
    private FamilyControllerClient familyControllerClient;
    @Autowired
    private StudentControllerClient studentControllerClient;
    @Autowired
    private TeacherControllerClient teacherControllerClient;
    /**
     * 用户激活接口
     * @param
     * @return
     */
    @ApiOperation(value = "用户激活接口")
    @RequestMapping(value = "/activationBaseUser",method = RequestMethod.POST)
    public Object activationBaseUser(@RequestBody BaseUserQueryDto baseUserQueryDto){
        //查询出当前激活用户的类型 1、学生，2、家长，3、老师
     /*   baseUserControllerClient*/
        return WrapMapper.ok();
    }
}