package com.easyim.comm.server.handler;

import com.easyim.comm.message.heartbeat.PingMessage;
import com.easyim.comm.message.heartbeat.PongMessage;
import com.easyim.comm.server.common.SnowflakeIDGenerator;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 心跳包处理器
 *
 * @author 单程车票
 */
@Slf4j
@Component
@ChannelHandler.Sharable
public class HeartBeatHandler extends SimpleChannelInboundHandler<PingMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, PingMessage msg) throws Exception {
        log.info("收到心跳包处理请求");
        // 收到客户端的心跳包后发送响应心跳包
        ctx.writeAndFlush(new PongMessage(SnowflakeIDGenerator.generateID()));
    }

}
