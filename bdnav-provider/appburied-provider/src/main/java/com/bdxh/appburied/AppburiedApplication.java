package com.bdxh.appburied;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @description: 应用上报启动类
 * @author: xuyuan
 * @create: 2019-04-10 18:28
 **/
@SpringBootApplication
@ServletComponentScan
@EnableEurekaClient
@EnableApolloConfig
public class AppburiedApplication {
    public static void main(String[] args) {
        SpringApplication.run(AppburiedApplication.class,args);
    }
}
