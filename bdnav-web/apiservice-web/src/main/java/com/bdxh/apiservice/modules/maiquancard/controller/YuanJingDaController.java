package com.bdxh.apiservice.modules.maiquancard.controller;

import com.alibaba.fastjson.JSONObject;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.school.entity.SchoolUser;
import com.bdxh.school.feign.SchoolUserControllerClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:登录进入麦圈的后台
 * @author: binzh
 * @create: 2019-05-05 11:34
 **/
@Slf4j
@RestController
@RequestMapping("/yuanJingDa")
@Validated
@Api(value = "集成远景达系统API", tags = "集成远景达系统API")
public class YuanJingDaController {
    @Autowired
    private SchoolUserControllerClient schoolUserControllerClient;

    @ApiOperation("获取登录参数接口")
    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public Object login(@RequestParam("userName")String userName,@RequestParam("schoolId")Long schoolId){
        JSONObject jsonObject=new JSONObject();
       SchoolUser schoolUser=schoolUserControllerClient.findSchoolUserByUserNameAndSchoolId(schoolId,userName).getResult();
        jsonObject.put("username",schoolUser.getUserName());
        jsonObject.put("password",schoolUser.getPassword());
        return WrapMapper.ok(jsonObject);
    }
}