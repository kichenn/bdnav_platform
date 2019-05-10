package com.bdxh.client.controller.Integrate.yuanjingda;

import com.alibaba.fastjson.JSONObject;
import com.bdxh.client.configration.security.utils.SecurityUtils;
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
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:登录进入麦圈的后台
 * @author: binzh
 * @create: 2019-05-05 11:34
 **/
@Slf4j
@RestController
@RequestMapping("/yuanJingDaLogin")
@Validated
@Api(value = "集成远景达系统API", tags = "集成远景达系统API")
public class YuanJingDaLogin{
    @Autowired
    private SchoolUserControllerClient schoolUserControllerClient;
    @ApiOperation("获取登录参数接口")
    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public Object login(){
        SchoolUser schoolUser=SecurityUtils.getCurrentUser();
        JSONObject jsonObject=new JSONObject();
       SchoolUser schoolUsers=schoolUserControllerClient.findSchoolUserByName(schoolUser.getUserName()).getResult();
        jsonObject.put("username",schoolUsers.getUserName());
        jsonObject.put("password",schoolUsers.getPassword());
        return WrapMapper.ok(jsonObject);
    }
}