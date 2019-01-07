package com.bdxh.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @description:
 * @author: xuyuan
 * @create: 2018-12-14 22:36
 **/
@EnableZuulProxy
@EnableEurekaClient
@SpringBootApplication
public class GatewayZuulServer {
    public static void main(String[] args) {
        SpringApplication.run(GatewayZuulServer.class,args);
    }
}
