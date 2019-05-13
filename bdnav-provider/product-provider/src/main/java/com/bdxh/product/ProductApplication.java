package com.bdxh.product;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @description: 商品启动类
 * @author: xuyuan
 * @create: 2019-01-19 16:19
 **/
@SpringBootApplication
@ServletComponentScan
@EnableEurekaClient
@EnableApolloConfig
public class ProductApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProductApplication.class,args);
    }
}
