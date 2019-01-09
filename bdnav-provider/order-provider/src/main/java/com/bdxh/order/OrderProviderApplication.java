package com.bdxh.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @description: 订单服务启动类
 * @author: xuyuan
 * @create: 2019-01-09 09:50
 **/
@SpringBootApplication
@EnableEurekaClient
public class OrderProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderProviderApplication.class,args);
    }
}
