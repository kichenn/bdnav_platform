package com.bdxh.pay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @description: 支付系统启动类
 * @author: xuyuan
 * @create: 2019-01-14 10:43
 **/
@SpringBootApplication
@EnableEurekaClient
public class PayProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(PayProviderApplication.class,args);
    }
}
