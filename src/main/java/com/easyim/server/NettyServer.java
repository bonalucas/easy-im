package com.easyim.server;

import com.easyim.common.NettyProperties;
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
     * 获取启动后的通道（用于主动发送消息）
     */
    private Channel channel;

    /**
     * 启动尝试次数
     */
    private static final int MAX_RETRY = 5;

    /**
     * 负责 ServerChannel 的线程池
     */
    private final EventLoopGroup serverChannelGroup = new NioEventLoopGroup(2);

    /**
     * 负责子 Channel 的线程池
     */
    private final EventLoopGroup channelGroup = new NioEventLoopGroup();

    @Autowired
    private NettyProperties nettyProperties;

    @Autowired
    private ServerChannelInitializer serverChannelInitializer;

    /**
     * 服务器启动方法
     */
    public void startServer() {
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(serverChannelGroup, channelGroup)
                    .channel(NioServerSocketChannel.class)
                    // 设置请求的队列的最大长度
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    // 开启 TCP 心跳机制
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    // 开启 Nagle 算法（保证高实时性）
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    .childHandler(serverChannelInitializer);
            // 绑定方法
            bind(bootstrap, nettyProperties.getIp(), nettyProperties.getPort(), MAX_RETRY);
        } catch (Exception e) {
            log.error("Netty 服务器启动异常：{}", e.getMessage());
            destroyEventLoop();
        }
    }

    /**
     * 服务器绑定方法
     *
     * @param bootstrap bootstrap
     * @param hostname ip
     * @param port 端口
     * @param retry 尝试次数
     */
    private void bind(ServerBootstrap bootstrap, String hostname, int port, int retry) {
        bootstrap.bind(new InetSocketAddress(hostname, port)).addListener(future -> {
            if (future.isSuccess()) {
                ChannelFuture channelFuture = (ChannelFuture) future;
                this.channel = channelFuture.channel();
                log.info("Netty 服务器启动成功 => Channel：{}", this.channel.localAddress());
            } else if (retry == 0) {
                log.error("终止 Netty 服务器启动：尝试 {} 次重启失败", MAX_RETRY);
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

    /**
     * 优雅关闭 EventLoop
     */
    public void destroyEventLoop() {
        serverChannelGroup.shutdownGracefully();
        channelGroup.shutdownGracefully();
    }

    /**
     * 获取与客户端建立连接的通道
     *
     * @return 通道
     */
    public Channel channel() {
        return this.channel;
    }

}
