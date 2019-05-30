package com.bdxh.weixiao;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @Description: 微校启动类
 * @Author: Kang
 * @Date: 2019/4/17 11:32
 */
@SpringBootApplication
@ServletComponentScan
@EnableEurekaClient
@EnableApolloConfig
public class WeiXiaoApplication {
    public static void main(String[] args) {
        SpringApplication.run(WeiXiaoApplication.class, args);
    }
}
