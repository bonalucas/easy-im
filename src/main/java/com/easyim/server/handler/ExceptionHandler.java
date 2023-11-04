package com.easyim.server.handler;

import com.easyim.comm.message.error.ErrorResponseMessage;
import com.easyim.common.Constants;
import com.easyim.common.EasyIMException;
import com.easyim.common.ServerSessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * 异常处理器
 *
 * @author 单程车票
 */
@Slf4j
@Component
@ChannelHandler.Sharable
public class ExceptionHandler extends ChannelInboundHandlerAdapter {

    /**
     * 静态内部类（单例模式）
     */
    private static class ExceptionHandlerInstance {
        private static final ExceptionHandler INSTANCE = new ExceptionHandler();
    }

    /**
     * 获取单例模式下的实例
     */
    public static ExceptionHandler getInstance() {
        return ExceptionHandlerInstance.INSTANCE;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (cause instanceof EasyIMException) {
            EasyIMException error = (EasyIMException) cause;
            // 业务异常返回客户端异常消息
            ctx.writeAndFlush(new ErrorResponseMessage(error.getMessageId(), error.getMessage()));
        } else {
            // 系统异常关闭连接
            String meetingID = (String) ctx.channel().attr(AttributeKey.valueOf(Constants.AttributeKeyName.MEETING_ID)).get();
            if (StringUtils.isNotEmpty(meetingID)) ServerSessionUtil.leaveMeeting(meetingID, ctx.channel());
            log.error("客户端 【{}】 通信异常，断开连接 【cause：{}】", ctx.channel().remoteAddress(), cause.getMessage());
            ctx.close();
        }
    }

}
