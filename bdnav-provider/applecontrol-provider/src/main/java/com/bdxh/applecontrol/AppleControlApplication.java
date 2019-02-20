package com.bdxh.applecontrol;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @description: Apple管控启动类
 * @author: xuyuan
 * @create: 2019-02-20 14:25
 **/
@SpringBootApplication
@ServletComponentScan
@EnableEurekaClient
public class AppleControlApplication {
    public static void main(String[] args) {
        SpringApplication.run(AppleControlApplication.class,args);
    }
}
