package com.bdxh.appmarket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @description: 应用市场启动类
 * @author: xuyuan
 * @create: 2019-03-04 15:10
 **/
@SpringBootApplication
@ServletComponentScan
@EnableEurekaClient
public class AppmarketApplication {
    public static void main(String[] args) {
        SpringApplication.run(AppmarketApplication.class,args);
    }
}
