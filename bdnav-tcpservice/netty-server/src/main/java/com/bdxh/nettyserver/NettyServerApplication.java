package com.bdxh.nettyserver;

import com.bdxh.nettyserver.netty.TcpServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @description: netty服务端启动类
 * @author: xuyuan
 * @create: 2019-03-14 18:05
 **/
@SpringBootApplication
public class NettyServerApplication {
    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(NettyServerApplication.class, args);
        TcpServer tcpServer = applicationContext.getBean(TcpServer.class);
        tcpServer.start();
    }
}
