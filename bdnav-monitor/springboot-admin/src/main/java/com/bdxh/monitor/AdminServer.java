package com.bdxh.monitor;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @description:
 * @author: xuyuan
 * @create: 2018-12-15 22:04
 **/
@SpringBootApplication
@EnableAdminServer
@EnableEurekaClient
public class AdminServer {
    public static void main(String[] args) {
        SpringApplication.run(AdminServer.class,args);
    }
}
