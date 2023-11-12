package com.easyim.server.handler;

import com.easyim.comm.message.error.ErrorResponseMessage;
import com.easyim.comm.message.meeting.LeaveMeetingResponseMessage;
import com.easyim.common.Constants;
import com.easyim.common.EasyIMException;
import com.easyim.common.ServerSessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
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
            String nickname = (String) ctx.channel().attr(AttributeKey.valueOf(Constants.AttributeKeyName.USER_NAME)).get();
            if (StringUtils.isNotEmpty(meetingID)) {
                // 移除会议缓存
                ServerSessionUtil.leaveMeeting(meetingID, ctx.channel());
                // 告知其他用户
                ChannelGroup channelGroup = ServerSessionUtil.getMeetingChannel(meetingID);
                if (channelGroup == null) return;
                for (Channel channel : channelGroup) {
                    channel.writeAndFlush(new LeaveMeetingResponseMessage(nickname));
                }
            }
            log.error("客户端 【{}】 通信异常，断开连接 【cause：{}】", ctx.channel().remoteAddress(), cause.getMessage());
            ctx.close();
        }
    }

}
