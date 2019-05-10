package com.bdxh.backend.configration.common;

import com.bdxh.common.utils.HttpPoolClientUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description:
 * @author: Kang
 * @create: 2019-02-20 00:37
 **/
@Configuration
@Slf4j
public class HttpPoolClientConfig {

    /**
     * 最大连接数
     */
    @Value("${httpPool.maxTotal:800}")
    private int maxTotal;

    /**
     * 并发数
     */
    @Value("${httpPool.maxPerRoute:}50")
    private int maxPerRoute;

    /**
     * 获取连接超时时间
     */
    @Value("${httpPool.connectRequestTimeout:2000}")
    private int connectRequestTimeout;

    /**
     * 连接超时时间
     */
    @Value("${httpPool.connectTimeout:3000}")
    private int connectTimeout;

    /**
     * 读取超时时间
     */
    @Value("${httpPool.socketTimeout:5000}")
    private int socketTimeout;

    @Bean(initMethod = "init",destroyMethod = "shutdown")
    @ConditionalOnExpression("${httpPool.enable:true}")
    public HttpPoolClientUtils httpPoolClientUtils(){
        log.info("httpPoolClient初始化开始--------------------------------------");
        HttpPoolClientUtils httpPoolClientUtils = new HttpPoolClientUtils(maxTotal,maxPerRoute,connectRequestTimeout,connectTimeout,socketTimeout);
        return httpPoolClientUtils;
    }

}
