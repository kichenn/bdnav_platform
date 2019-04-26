package com.bdxh.servicepermit;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
* @Description: 商品服务权限启动类
* @Author: Kang
* @Date: 2019/4/26 11:25
*/
@SpringBootApplication
@ServletComponentScan
@EnableEurekaClient
@EnableApolloConfig
public class ServicePermitApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServicePermitApplication.class, args);
    }
}
