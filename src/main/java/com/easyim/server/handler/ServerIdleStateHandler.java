package com.easyim.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 心跳检测处理器
 *
 * @author 单程车票
 */
@Slf4j
public class ServerIdleStateHandler extends IdleStateHandler {

    private static final long READER_IDLE_TIME = 15L;

    public ServerIdleStateHandler() {
        super(READER_IDLE_TIME, 0L, 0L, TimeUnit.SECONDS);
    }

    @Override
    protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) {
        log.info("{} 秒内未读到数据，关闭连接", READER_IDLE_TIME);
        ctx.channel().close();
    }

}
