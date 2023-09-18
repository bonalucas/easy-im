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
public class NettyServer implements Callable<Channel> {

    private final EventLoopGroup connectionGroup = new NioEventLoopGroup(2);
    private final EventLoopGroup workerGroup = new NioEventLoopGroup();
    private Channel channel;

    @Autowired
    private NettyProperties nettyProperties;

    @Autowired
    private ServerChannelInitializer serverChannelInitializer;

    @Override
    public Channel call() throws Exception {
        ChannelFuture channelFuture = null;
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(connectionGroup, workerGroup);
            bootstrap.channel(NioServerSocketChannel.class);
            bootstrap.option(ChannelOption.SO_BACKLOG, 128);
            bootstrap.childHandler(serverChannelInitializer);
            channelFuture = bootstrap.bind(new InetSocketAddress(nettyProperties.getIp(), nettyProperties.getPort())).syncUninterruptibly();
            this.channel = channelFuture.channel();
        } catch (Exception e) {
            log.error("服务器启动异常：{}", e.getMessage());
        } finally {
            if (null != channelFuture && channelFuture.isSuccess()) {
                log.info("服务器启动成功");
            } else {
                log.error("服务器启动失败");
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
