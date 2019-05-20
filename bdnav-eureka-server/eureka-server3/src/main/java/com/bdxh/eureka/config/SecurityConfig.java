package com.bdxh.eureka.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @Description: eurekaServer 安全配置
 * @Author: Kang
 * @Date: 2019/5/17 11:15
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // #忽略springboot 监控的路径
        http.csrf().disable().headers().frameOptions().disable().and().authorizeRequests().antMatchers("/actuator/**").permitAll();
        super.configure(http);
    }

}
