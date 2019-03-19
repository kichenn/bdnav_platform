package com.bdxh.nettyserver.netty.config;

import com.bdxh.nettyserver.netty.handler.IdleServerHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@Qualifier("serverChannelInitializer")
public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {

    /**
     * 读操作空闲20秒
     */
    private final static int READER_IDLE_TIME_SECONDS = 10;

    /**
     * 写操作空闲20秒
     */
    private final static int WRITER_IDLE_TIME_SECONDS = 10;

    /**
     * 读写全部空闲40秒
     */
    private final static int ALL_IDLE_TIME_SECONDS = 20;

    @Autowired
    private IdleServerHandler idleServerHandler;

    @Override
    protected void initChannel(SocketChannel socketChannel) {
    	ChannelPipeline p = socketChannel.pipeline();
    	p.addLast("idleStateHandler", new IdleStateHandler(READER_IDLE_TIME_SECONDS
    			, WRITER_IDLE_TIME_SECONDS, ALL_IDLE_TIME_SECONDS, TimeUnit.SECONDS));
	    p.addLast("idleServerHandler", idleServerHandler);
    }

}
