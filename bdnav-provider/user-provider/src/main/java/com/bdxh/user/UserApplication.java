package com.bdxh.user;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @description: 用户信息服务启动类
 * @author: xuyuan
 * @create: 2019-02-25 18:34
 **/
@SpringBootApplication
@ServletComponentScan
@EnableEurekaClient
@EnableApolloConfig
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class,args);
    }

}
