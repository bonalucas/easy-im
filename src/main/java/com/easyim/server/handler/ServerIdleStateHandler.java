package com.easyim.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
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

    private static final long READER_IDLE_TIME = 60L;

    public ServerIdleStateHandler() {
        super(READER_IDLE_TIME, 0L, 0L, TimeUnit.SECONDS);
    }

    @Override
    protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) {
        IdleState state = evt.state();
        if (state == IdleState.READER_IDLE) {
            log.info("{} 秒内未读到数据，关闭服务器连接", READER_IDLE_TIME);
            // 关闭对应的客户端连接
            ctx.channel().close();
        }
    }

}
