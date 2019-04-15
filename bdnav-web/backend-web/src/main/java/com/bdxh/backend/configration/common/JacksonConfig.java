package com.bdxh.backend.configration.common;

import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description: jackson序列化
 * @author: xuyuan
 * @create: 2019-04-15 09:54
 **/
@Configuration
public class JacksonConfig {

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return (jacksonObjectMapperBuilder) -> jacksonObjectMapperBuilder.serializerByType(Long.TYPE, ToStringSerializer.instance);
    }

}
