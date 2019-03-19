package com.bdxh.nettyserver.netty.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @description: netty属性配置类
 * @author: xuyuan
 * @create: 2019-03-12 10:58
 **/
@Data
@ConfigurationProperties(prefix = "netty")
public class NettyProperties {

    private int tcpPort = 9030;

    private int bossCount = 2;

    private int workerCount = 4;

    private boolean keepAlive = true;

    private int backlog = 200;

}
