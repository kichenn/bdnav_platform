package com.bdxh.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @description:
 * @author: xuyuan
 * @create: 2018-12-14 21:56
 **/
@SpringBootApplication
@EnableEurekaServer
public class EurekaServer1 {
    public static void main(String[] args) {
        SpringApplication.run(EurekaServer1.class,args);
    }
}
