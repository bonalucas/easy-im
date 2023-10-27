package com.easyim.comm.server.handler;

import com.easyim.comm.server.common.ServerChannelUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 监控处理器
 *
 * @author 单程车票
 */
@Slf4j
@Component
@ChannelHandler.Sharable
public class MonitorHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        // 监控日志
        log.info("客户端 {} 建立连接", ctx.channel().remoteAddress());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        // 客户端断开连接，清空 ServerChannel 缓存
        ServerChannelUtil.removeChannelByChannelId(ctx.channel().id().toString());
        ServerChannelUtil.removeAllGroup(ctx.channel());
        // 监控日志
        log.info("客户端断开连接：{}", ctx.channel());
        // 关闭连接
        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // 客户端异常断开连接，清空 ServerChannel 缓存
        ServerChannelUtil.removeChannelByChannelId(ctx.channel().id().toString());
        ServerChannelUtil.removeAllGroup(ctx.channel());
        // 监控日志
        log.error("服务器异常断开连接：{}", cause.getMessage());
        // 关闭连接
        ctx.close();
    }

}
