package com.bdxh.onecard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @description: 一卡通服务启动类
 * @author: xuyuan
 * @create: 2019-01-10 19:14
 **/
@SpringBootApplication
@ServletComponentScan
@EnableEurekaClient
@EnableFeignClients(basePackages = {"com.bdxh"})
@EnableCircuitBreaker
public class OnecardApplication {
    public static void main(String[] args) {
        SpringApplication.run(OnecardApplication.class,args);
    }
}
