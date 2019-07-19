package com.bdxh.pay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @description: 支付系统启动类
 * @author: xuyuan
 * @create: 2019-01-14 10:43
 **/
@SpringBootApplication(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
@EnableEurekaClient
@ComponentScan(basePackages = {"com.bdxh.pay","com.bdxh.order","com.bdxh.servicepermit","com.bdxh.user","com.bdxh.wallet"})
@EnableFeignClients(basePackages = {"com.bdxh.pay","com.bdxh.order","com.bdxh.servicepermit","com.bdxh.user","com.bdxh.wallet"})
public class PayProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(PayProviderApplication.class,args);
    }
}
