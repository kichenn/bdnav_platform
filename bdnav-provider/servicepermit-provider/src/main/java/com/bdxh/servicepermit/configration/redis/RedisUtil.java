package com.bdxh.servicepermit.configration.redis;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCluster;


/**
 * @Description: redis相关操作
 * @Author: Kang
 * @Date: 2019/5/5 18:05
 */
@Component
public class RedisUtil {

    @Autowired
    private JedisCluster jedisCluster;

    /**
     * 设置缓存
     *
     * @param key   缓存key
     * @param value 缓存value
     */
    public void set(String key, String value) {
        jedisCluster.set(key, value);
    }

    /**
     * 设置缓存对象
     *
     * @param key 缓存key
     * @param obj 缓存value
     */
    public <T> void setObject(String key, T obj, int expireTime) {
        jedisCluster.setex(key, expireTime, JSON.toJSONString(obj));
    }

    /**
     * 获取指定key的缓存
     *
     * @param key---JSON.parseObject(value, User.class);
     */
    public String getObject(String key) {
        return jedisCluster.get(key);
    }

    /**
     * 判断当前key值 是否存在
     *
     * @param key
     */
    public boolean hasKey(String key) {
        return jedisCluster.exists(key);
    }


    /**
     * 设置缓存，并且自己指定过期时间
     *
     * @param key
     * @param value
     * @param expireTime 过期时间（秒）
     */
    public void setWithExpireTime(String key, String value, int expireTime) {
        jedisCluster.setex(key, expireTime, value);
    }


    /**
     * 获取指定key的缓存
     *
     * @param key
     */
    public String get(String key) {
        String value = jedisCluster.get(key);
        return value;
    }

    /**
     * 删除指定key的缓存
     *
     * @param key
     */
    public void delete(String key) {
        jedisCluster.del(key);
    }

    /**
     * @param key
     * @throws
     * @Title: expire
     * @Description: 更新key的失效时间
     */
    public Long expire(String key, int seconds) {
        return jedisCluster.expire(key, seconds);
    }

    public Long pexpire(String key, long seconds) {
        return jedisCluster.pexpire(key, seconds);
    }

}
