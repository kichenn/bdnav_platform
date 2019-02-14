package com.bdxh.mapservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @description: 地图服务启动类
 * @author: xuyuan
 * @create: 2019-02-14 10:50
 **/
@SpringBootApplication
@ServletComponentScan
@EnableEurekaClient
public class MapserviceApplication {
    public static void main(String[] args) {
        SpringApplication.run(MapserviceApplication.class,args);
    }
}
