package com.bdxh.product.controller;

import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @description: 测试控制器
 * @author: xuyuan
 * @create: 2019-02-19 15:38
 **/
@Controller
@RequestMapping("/xy")
public class TestController {

    @RequestMapping("/demo")
    @ResponseBody
    public String demo(String xc,String name,String pass){
        return xc+":"+name+":"+pass;
    }

    @RequestMapping("/demo1")
    @ResponseBody
    public MyUser demo(@RequestBody MyUser myUser){
        return myUser;
    }

    @Data
    @XmlRootElement(name = "xml")
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class MyUser{

        @XmlElement(name="userName")
        private String userName="123";

        @XmlElement(name = "password")
        private String password="456";

    }

}
