package com.bdxh.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * 后台项目启动类
 */
@SpringBootApplication
@EnableEurekaClient
public class BackendProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(BackendProviderApplication.class,args);
    }
}
