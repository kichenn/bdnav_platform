package com.bdxh.app.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 测试控制器
 * @author: xuyuan
 * @create: 2019-03-07 17:28
 **/
@RestController
@RequestMapping("/order")
public class TestController {

    @RequestMapping("hello")
    public Object hello(Authentication authentication){
        return "xy";
    }

}
