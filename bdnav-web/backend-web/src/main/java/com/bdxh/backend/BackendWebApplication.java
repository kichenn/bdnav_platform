package com.bdxh.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @description: 后台管理系统启动类
 * @author: xuyuan
 * @create: 2019-02-27 15:38
 **/
@SpringBootApplication
@ServletComponentScan
@EnableEurekaClient
@ComponentScan(basePackages = {"com.bdxh"})
@EnableFeignClients(basePackages = {"com.bdxh"})
@EnableCircuitBreaker
public class BackendWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(BackendWebApplication.class,args);
    }
}
