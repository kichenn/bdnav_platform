package com.bdxh.onecard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @description: 钱包项目启动类
 * @author: xuyuan
 * @create: 2019-01-02 14:25
 **/
@SpringBootApplication
@ServletComponentScan
@EnableEurekaClient
@ComponentScan(basePackages = {"com.bdxh"})
@EnableFeignClients(basePackages = {"com.bdxh"})
@EnableCircuitBreaker
@EnableCaching
public class OnecardProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(OnecardProviderApplication.class,args);
    }
}
