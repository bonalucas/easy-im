package com.easyim.server;

import com.easyim.config.NettyProperties;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

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
            // 设置请求的队列的最大长度
            bootstrap.option(ChannelOption.SO_BACKLOG, 1024);
            // 开启 TCP 心跳机制
            bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
            // 开启 Nagle 算法（保证高实时性）
            bootstrap.childOption(ChannelOption.TCP_NODELAY, true);
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
