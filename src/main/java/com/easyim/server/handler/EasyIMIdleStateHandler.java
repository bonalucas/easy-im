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
public class EasyIMIdleStateHandler extends IdleStateHandler {

    /**
     * 服务器读事件间隔时间
     */
    private static final long READER_IDLE_TIME = 60L * 1000;

    public EasyIMIdleStateHandler() {
        super(READER_IDLE_TIME, 0L, 0L, TimeUnit.MILLISECONDS);
    }

    @Override
    protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) {
        if (evt.state() == IdleState.READER_IDLE) {
            log.debug("{} ms内未读到数据，关闭服务器连接", READER_IDLE_TIME);
            ctx.close();
        }
    }

}
