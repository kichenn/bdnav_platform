package com.bdxh.weixiao.controller;

import com.bdxh.weixiao.configration.security.entity.UserInfo;
import com.bdxh.weixiao.configration.security.utils.SecurityContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 测试控制器
 * @author: xuyuan
 * @create: 2019-03-14 16:27
 **/
@RestController
@RequestMapping("/test")
public class TestController {

    @RequestMapping("/userInfo")
    public Object getLoginUser(){
        UserInfo userInfo = SecurityContext.getUserInfo();
        return userInfo;
    }

}
