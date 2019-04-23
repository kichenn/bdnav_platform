package com.bdxh.weixiao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @description: weixiao服务启动类
 * @author: xuyuan
 * @create: 2019-03-13 10:02
 **/
@SpringBootApplication
@ServletComponentScan
@EnableEurekaClient
@ComponentScan(basePackages = {"com.bdxh"})
@EnableFeignClients(basePackages = {"com.bdxh"})
public class WeixiaoWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(WeixiaoWebApplication.class,args);
    }
}
