package com.bdxh.weixiao.configration.controller;

import com.bdxh.weixiao.configration.security.utils.SecurityContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 测试控制器
 * @author: xuyuan
 * @create: 2019-03-13 18:27
 **/
@RestController
@RequestMapping("/test")
public class TestController {

    @RequestMapping("/xy")
    public Object getUserInfo(){
        return SecurityContext.getUserInfo();
    }

}
