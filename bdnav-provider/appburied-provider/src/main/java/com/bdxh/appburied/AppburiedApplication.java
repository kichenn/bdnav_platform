package com.bdxh.appburied;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @description: 应用埋点启动类
 * @author: xuyuan
 * @create: 2019-04-10 18:28
 **/
@SpringBootApplication
@ServletComponentScan
@EnableEurekaClient
public class AppburiedApplication {
    public static void main(String[] args) {
        SpringApplication.run(AppburiedApplication.class,args);
    }
}
