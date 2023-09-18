package com.easyim.server.handler;

import com.easyim.server.util.SocketChannelUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 基础处理器
 *
 * @author 单程车票
 */
@Slf4j
public abstract class BaseHandler<T> extends SimpleChannelInboundHandler<T> {

    public abstract void channelRead(Channel channel, T msg);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, T msg) throws Exception {
        channelRead(ctx.channel(), msg);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        log.info("客户端建立连接：{}", ctx.channel());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        SocketChannelUtil.removeChannelByChannelId(ctx.channel().id().toString());
        SocketChannelUtil.removeAllGroup(ctx.channel());
        log.info("客户端断开连接：{}", ctx.channel());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        SocketChannelUtil.removeChannelByChannelId(ctx.channel().id().toString());
        SocketChannelUtil.removeAllGroup(ctx.channel());
        log.error("服务器异常断开连接：{}", cause.getMessage());
    }
}
