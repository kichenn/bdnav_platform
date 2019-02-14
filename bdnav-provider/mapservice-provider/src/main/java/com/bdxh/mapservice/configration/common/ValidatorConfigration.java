package com.bdxh.mapservice.configration.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

/**
 * @description: 校验器配置类
 * @author: xuyuan
 * @create: 2019-01-10 11:48
 **/
@Configuration
public class ValidatorConfigration {
    
    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor(){
        return new MethodValidationPostProcessor();
    }

}
