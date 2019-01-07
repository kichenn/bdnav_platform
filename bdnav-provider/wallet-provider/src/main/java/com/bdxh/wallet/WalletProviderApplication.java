package com.bdxh.wallet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @description: 钱包项目启动类
 * @author: xuyuan
 * @create: 2019-01-02 14:25
 **/
@SpringBootApplication
@EnableEurekaClient
public class WalletProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(WalletProviderApplication.class,args);
    }
}
