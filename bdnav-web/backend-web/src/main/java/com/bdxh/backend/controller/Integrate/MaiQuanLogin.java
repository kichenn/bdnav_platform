package com.bdxh.backend.controller.Integrate;

import com.alibaba.fastjson.JSONObject;
import com.bdxh.backend.configration.security.utils.SecurityUtils;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.school.entity.SchoolUser;
import com.bdxh.school.feign.SchoolUserControllerClient;
import com.bdxh.system.entity.User;
import io.swagger.annotations.Api;
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
@RequestMapping("/maiQuanLogin")
@Validated
@Api(value = "系统集成麦圈系统API", tags = "系统集成麦圈系统API")
public class MaiQuanLogin {
    @Autowired
    private SchoolUserControllerClient schoolUserControllerClient;
    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public Object login(){
        User schoolUser= SecurityUtils.getCurrentUser();
        SchoolUser schoolUsers=schoolUserControllerClient.findSchoolUserByName(schoolUser.getUserName()).getResult();
         return WrapMapper.ok(schoolUsers);
    }
}