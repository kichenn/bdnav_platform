package com.bdxh.servicepermit.configration.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

/**
 * @Description: 校验器配置类
 * @Author: Kang
 * @Date: 2019/4/26 10:55
 */
@Configuration
public class ValidatorConfigration {

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }

}
