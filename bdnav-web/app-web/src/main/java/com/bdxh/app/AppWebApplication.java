package com.bdxh.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @description: app服务启动类
 * @author: xuyuan
 * @create: 2019-03-07 09:55
 **/
@SpringBootApplication
@ServletComponentScan
@EnableEurekaClient
public class AppWebApplication {
    public static void main(String[] args) {
       SpringApplication.run(AppWebApplication.class,args);
    }
}
