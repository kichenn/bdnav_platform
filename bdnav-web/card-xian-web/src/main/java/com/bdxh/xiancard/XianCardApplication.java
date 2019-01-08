package com.bdxh.xiancard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @description: 西安一卡通启动类
 * @author: xuyuan
 * @create: 2019-01-08 17:22
 **/
@SpringBootApplication
@ServletComponentScan
@EnableEurekaClient
@ComponentScan(basePackages = {"com.bdxh"})
@EnableFeignClients(basePackages = {"com.bdxh"})
@EnableCircuitBreaker
public class XianCardApplication {
    public static void main(String[] args) {
        SpringApplication.run(XianCardApplication.class,args);
    }
}
