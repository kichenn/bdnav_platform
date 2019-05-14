package com.bdxh.weixiao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Description: weixiao服务启动类
 * @Author: Kang
 * @Date: 2019/5/14 18:08
 */
@SpringBootApplication
@ServletComponentScan
@EnableEurekaClient
@ComponentScan(basePackages = {"com.bdxh"})
@EnableFeignClients(basePackages = {"com.bdxh"})
public class WeixiaoWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(WeixiaoWebApplication.class, args);
    }
}
