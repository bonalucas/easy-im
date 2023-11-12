package com.easyim.server.handler.biz;

import com.alibaba.fastjson.JSON;
import com.easyim.comm.message.screen.ExitScreenRequestMessage;
import com.easyim.comm.message.screen.ExitScreenResponseMessage;
import com.easyim.common.Constants;
import com.easyim.common.EasyIMException;
import com.easyim.common.ServerSessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;

/**
 * 退出屏幕共享处理器
 *
 * @author 单程车票
 */
@Slf4j
@ChannelHandler.Sharable
public class ExitScreenHandler extends SimpleChannelInboundHandler<ExitScreenRequestMessage> {

    /**
     * 静态内部类（单例模式）
     */
    private static class ExitScreenHandlerInstance {
        private static final ExitScreenHandler INSTANCE = new ExitScreenHandler();
    }

    /**
     * 获取单例模式下的实例
     */
    public static ExitScreenHandler getInstance() {
        return ExitScreenHandlerInstance.INSTANCE;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ExitScreenRequestMessage msg) throws Exception {
        log.debug("退出屏幕共享消息处理请求：{}", JSON.toJSONString(msg));
        // 获取通道属性
        String meetingId = (String) ctx.channel().attr(AttributeKey.valueOf(Constants.AttributeKeyName.MEETING_ID)).get();
        String nickname = (String) ctx.channel().attr(AttributeKey.valueOf(Constants.AttributeKeyName.USER_NAME)).get();
        if (meetingId == null || nickname == null) throw new EasyIMException(msg.getMessageId(), Constants.EasyIMError.USER_INFO_ERROR);
        // 通知会议其他人
        ChannelGroup channelGroup = ServerSessionUtil.getMeetingChannel(meetingId);
        if (channelGroup == null) throw new EasyIMException(msg.getMessageId(), Constants.EasyIMError.MEETING_NO_EXIST);
        ExitScreenResponseMessage message = new ExitScreenResponseMessage(nickname);
        for (Channel channel : channelGroup) {
            if (!channel.id().equals(ctx.channel().id())) channel.writeAndFlush(message);
        }
    }

}
