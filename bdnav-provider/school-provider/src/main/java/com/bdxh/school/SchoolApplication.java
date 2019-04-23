package com.bdxh.school;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @description: 学校基本信息启动类
 * @author: xuyuan
 * @create: 2019-02-25 10:30
 **/
@SpringBootApplication
@ServletComponentScan
@EnableEurekaClient
@EnableApolloConfig
public class SchoolApplication {
    public static void main(String[] args) {
        SpringApplication.run(SchoolApplication.class, args);
    }
}
