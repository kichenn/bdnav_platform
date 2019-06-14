package com.bdxh.task;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @Description: 定时任务启动类 (此处 basePackages用到哪个服务再去扫描某成服务 不可 com.bdxh)
 * @Author: Kang
 * @Date: 2019/4/29 14:13
 */
@SpringBootApplication
@EnableScheduling
@EnableApolloConfig
@ServletComponentScan
@ComponentScan(basePackages = {"com.bdxh.user","com.bdxh.school","com.bdxh.servicepermit","com.bdxh.task"})
@EnableFeignClients(basePackages = {"com.bdxh.user","com.bdxh.school","com.bdxh.servicepermit"})
public class TaskProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskProviderApplication.class, args);
    }

}
