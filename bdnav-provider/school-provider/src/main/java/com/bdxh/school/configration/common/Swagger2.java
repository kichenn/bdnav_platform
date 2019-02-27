package com.bdxh.school.configration.common;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.Contact;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableSwagger2
public class Swagger2 {

    @Bean
    public Docket createRestApi() {
//        List<Parameter> list = Arrays.asList(
//                new ParameterBuilder()
//                        .name("Authorization")
//                        .description("访问令牌")
//                        .modelRef(new ModelRef("string"))
//                        .parameterType("HEADER")
//                        .build()
//        );
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
//                .globalOperationParameters(list)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.bdxh.school"))
                .paths(PathSelectors.any())
                .build();
//                .ignoredParameterTypes(CurrentUser.class)
//                .ignoredParameterTypes(ParamToBody.class);
    }

    private ApiInfo apiInfo() {
        StringBuilder descBuilder = new StringBuilder();
        descBuilder.append("<h3><strong>嘤嘤嘤</strong></h3>");
        return new ApiInfoBuilder()
                .title("学校 APIs")
                .description(descBuilder.toString())
                .termsOfServiceUrl("http://www.test/")
                .contact(new Contact("Kang", "", ""))
                .version("1.0")
                .build();
    }
}
