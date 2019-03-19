package com.bdxh.nettyserver.netty.common;

import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description: channel连接保持类
 * @author: xuyuan
 * @create: 2019-03-12 11:12
 **/
public class ChannelRepository {

    private final static Map<String, Channel> channelCache = new ConcurrentHashMap<String, Channel>();

    public void put(String key, Channel value) {
        channelCache.put(key, value);
    }

    public Channel get(String key) {
        return channelCache.get(key);
    }

    public void remove(String key) {
        channelCache.remove(key);
    }

    public int size() {
        return channelCache.size();
    }

}
