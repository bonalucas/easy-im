package com.easyim.server;

import com.easyim.config.NettyProperties;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.util.concurrent.Callable;

/**
 * Netty 服务器
 *
 * @author 单程车票
 */
@Slf4j
@Component("nettyServer")
public class NettyServer {

    private final EventLoopGroup connectionGroup = new NioEventLoopGroup(2);
    private final EventLoopGroup workerGroup = new NioEventLoopGroup();
    private Channel channel;

    @Autowired
    private NettyProperties nettyProperties;

    @Autowired
    private ServerChannelInitializer serverChannelInitializer;

    public Channel startServer() {
        ChannelFuture channelFuture = null;
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(connectionGroup, workerGroup);
            bootstrap.channel(NioServerSocketChannel.class);
            // SO_BACKLOG 表示系统用于临时存放已完成三次握手的请求的队列的最大长度（如果连接建立频繁，服务器处理创建新连接较慢，则可以适当调大这个参数）
            bootstrap.option(ChannelOption.SO_BACKLOG, 1024);
            bootstrap.childHandler(serverChannelInitializer);
            channelFuture = bootstrap.bind(new InetSocketAddress(nettyProperties.getIp(), nettyProperties.getPort())).sync();
            this.channel = channelFuture.channel();
        } catch (Exception e) {
            log.error("Netty 服务器启动异常：{}", e.getMessage());
        } finally {
            if (null == channelFuture || !channelFuture.isSuccess()) {
                log.error("Netty 服务器启动失败");
            }
        }
        return channel;
    }

    public void destroy() {
        if (null == channel) return;
        channel.close();
        connectionGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }

    public Channel channel() {
        return channel;
    }

}
