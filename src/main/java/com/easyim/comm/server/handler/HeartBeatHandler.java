package com.easyim.comm.server.handler;

import com.alibaba.fastjson.JSON;
import com.easyim.comm.message.heartbeat.PingMessage;
import com.easyim.comm.message.heartbeat.PongMessage;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 心跳包处理器
 *
 * @author 单程车票
 */
@Slf4j
@ChannelHandler.Sharable
public class HeartBeatHandler extends SimpleChannelInboundHandler<PingMessage> {

    /**
     * 静态内部类（单例模式）
     */
    private static class HeartBeatHandlerInstance {
        private static final HeartBeatHandler INSTANCE = new HeartBeatHandler();
    }

    /**
     * 获取单例模式下的实例
     */
    public static HeartBeatHandler getInstance() {
        return HeartBeatHandlerInstance.INSTANCE;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, PingMessage msg) throws Exception {
        log.info("心跳消息处理请求：{}", JSON.toJSONString(msg));
        ctx.writeAndFlush(new PongMessage());
    }

}
