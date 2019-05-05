package com.bdxh.client.controller.sso;

import com.alibaba.fastjson.JSONObject;
import com.bdxh.client.configration.security.utils.SecurityUtils;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.school.entity.SchoolUser;
import com.bdxh.school.feign.SchoolUserControllerClient;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:登录进入麦圈的后台
 * @author: binzh
 * @create: 2019-05-05 11:34
 **/
@Slf4j
@RestController
@RequestMapping("/maiQuanLogin")
@Validated
@Api(value = "系统集成麦圈系统API", tags = "系统集成麦圈系统API")
public class MaiQuanLogin {
    @Autowired
    private SchoolUserControllerClient schoolUserControllerClient;
    @RequestMapping("/login")
    public Object login(){
        SchoolUser schoolUser=SecurityUtils.getCurrentUser();
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("username",schoolUser.getUserName());
        jsonObject.put("password",schoolUser.getPassword());
         return WrapMapper.ok(jsonObject);
    }
}