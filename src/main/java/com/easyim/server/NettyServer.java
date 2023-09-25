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
import java.util.concurrent.TimeUnit;

/**
 * Netty 服务器
 *
 * @author 单程车票
 */
@Slf4j
@Component("nettyServer")
public class NettyServer {

    /**
     * 启动尝试次数
     */
    private static final int MAX_RETRY = 5;

    /**
     * 连接线程池
     */
    private final EventLoopGroup connectionGroup = new NioEventLoopGroup(2);

    /**
     * 工作线程池
     */
    private final EventLoopGroup workerGroup = new NioEventLoopGroup();

    @Autowired
    private NettyProperties nettyProperties;

    @Autowired
    private ServerChannelInitializer serverChannelInitializer;

    public void startServer() {
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
            bind(bootstrap, nettyProperties.getIp(), nettyProperties.getPort(), MAX_RETRY);
        } catch (Exception e) {
            log.error("Netty 服务器启动异常：{}", e.getMessage());
            destroyEventLoop();
        }
    }

    private void bind(ServerBootstrap bootstrap, String hostname, int port, int retry) {
        bootstrap.bind(new InetSocketAddress(hostname, port)).addListener(future -> {
            if (future.isSuccess()) {
                ChannelFuture channelFuture = (ChannelFuture) future;
                log.info("Netty 服务器启动成功 => Channel：{}", channelFuture.channel().localAddress());
            } else if (retry == 0) {
                log.info("终止 Netty 服务器启动");
                destroyEventLoop();
            } else {
                // 获取间隔重新启动时间
                int order = (MAX_RETRY - retry) + 1;
                int delay = 1 << order;
                log.info("第 {} 次 尝试重新启动 Netty 服务器失败...", order);
                // 定时任务尝试重新启动
                bootstrap.config().group().schedule(() ->
                        bind(bootstrap, hostname, port, retry - 1), delay, TimeUnit.SECONDS);
            }
        });
    }

    public void destroyEventLoop() {
        connectionGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }

}
