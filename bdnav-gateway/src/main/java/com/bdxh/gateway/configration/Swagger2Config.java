package com.bdxh.gateway.configration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: swagger2配置类
 * @author: xuyuan
 * @create: 2019-02-26 16:08
 **/
@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Bean
    public Docket buildDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(buildApiInfo()).select().build();
    }

    private ApiInfo buildApiInfo() {
        return new ApiInfoBuilder()
                .title("北斗星航核心业务系统")
                .description("北斗星航核心业务系统接口文档")
                .version("1.0")
                .build();
    }


    @Bean("swaggerResourcesProvider")
    @Primary
    public DocumentationConfig documentationConfig(@Autowired RouteLocator routeLocator){
        DocumentationConfig documentationConfig = new DocumentationConfig(routeLocator);
        return documentationConfig;
    }



    public class DocumentationConfig implements SwaggerResourcesProvider {

        private final RouteLocator routeLocator;

        public DocumentationConfig(RouteLocator routeLocator){
            this.routeLocator=routeLocator;
        }

        @Override
        public List<SwaggerResource> get() {
            List<SwaggerResource> resources = new ArrayList<>();
            List<Route> routes = routeLocator.getRoutes();
            routes.forEach((route)->resources.add(swaggerResource(route.getLocation(),route.getFullPath().replace("**", "v2/api-docs"),"1.0")));
            return resources;
        }

        private SwaggerResource swaggerResource(String name, String location, String version) {
            SwaggerResource swaggerResource = new SwaggerResource();
            swaggerResource.setName(name);
            swaggerResource.setLocation(location);
            swaggerResource.setSwaggerVersion(version);
            return swaggerResource;
        }
    }

}
