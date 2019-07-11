package com.bdxh.wallet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Description: 钱包项目启动类
 * @Author: Kang
 * @Date: 2019/7/10 17:41
 */
@SpringBootApplication
@ServletComponentScan
@EnableEurekaClient
@ComponentScan(basePackages = {"com.bdxh"})
@EnableFeignClients(basePackages = {"com.bdxh"})
@EnableCircuitBreaker
@EnableCaching
public class WalletApplication {
    public static void main(String[] args) {
        SpringApplication.run(WalletApplication.class, args);
    }
}
