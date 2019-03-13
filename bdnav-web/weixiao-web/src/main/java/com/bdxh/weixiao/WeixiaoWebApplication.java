package com.bdxh.weixiao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @description: weixiao服务启动类
 * @author: xuyuan
 * @create: 2019-03-13 10:02
 **/
@SpringBootApplication
@ServletComponentScan
@EnableEurekaClient
public class WeixiaoWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(WeixiaoWebApplication.class,args);
    }
}
