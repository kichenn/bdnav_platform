package com.bdxh.weixiao.configration.redis;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;


/**
 * @Description: jedis相关配置(redis cluster集群方式 ， 不能使用redisTemple对象操作[会导致分片异常] ， 踩坑)
 * 异常信息:Cannot retrieve initial cluster partitions from initial URIs
 * @Author: Kang
 * @Date: 2019/5/5 18:00
 */
@Configuration
@Slf4j
public class JedisClusterConfig {

    @Value("${spring.redis.cluster.nodes}")
    private String nodes;

    @Value("${spring.redis.timeout}")
    private int commandTimeout;

    @Value("${spring.redis.lettuce.pool.max-active}")
    private int maxTotal;

    @Value("${spring.redis.lettuce.pool.max-idle}")
    private int maxIdle;

    @Value("${spring.redis.lettuce.pool.min-idle}")
    private int minIdle;

    @Value("${spring.redis.lettuce.pool.max-wait}")
    private int maxWait;

    @Value("${spring.redis.password}")
    private String password;

    @Bean
    public JedisCluster getJedisCluster() {
        log.info("redis，集群信息：{}", nodes);
        String[] serverArray = nodes.split(",");
        Set<HostAndPort> nodes = new HashSet<>();
        for (String ipPort : serverArray) {
            String[] ipPortPair = ipPort.split(":");
            nodes.add(new HostAndPort(ipPortPair[0].trim(), Integer.valueOf(ipPortPair[1].trim())));
        }
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        //最大连接数
        config.setMaxTotal(maxTotal);
        //最大空闲数
        config.setMaxIdle(maxIdle);
        //设置最小空闲数
        config.setMinIdle(minIdle);
        config.setMaxWaitMillis(maxWait);
        //在获取Jedis连接时，自动检验连接是否可用
        config.setTestOnBorrow(true);
        //在将连接放回池中前，自动检验连接是否有效
        config.setTestOnReturn(true);
        //自动测试池中的空闲连接是否都是可用连接
        config.setTestWhileIdle(true);
        //连接耗尽时是否阻塞, false报异常,ture阻塞直到超时,默认true
        config.setBlockWhenExhausted(false);
        //表示idle object evitor两次扫描之间要sleep的毫秒数
        config.setTimeBetweenEvictionRunsMillis(30000);
        //表示idle object evitor每次扫描的最多的对象数
        config.setNumTestsPerEvictionRun(10);
        //表示一个对象至少停留在idle状态的最短时间，然后才能被idle object evitor扫描并驱逐；这一项只有在timeBetweenEvictionRunsMillis大于0时才有意义
        config.setMinEvictableIdleTimeMillis(60000);
        //需要密码连接的创建对象方式
        return new JedisCluster(nodes, commandTimeout, 10000, 3, password, config);
    }
}
