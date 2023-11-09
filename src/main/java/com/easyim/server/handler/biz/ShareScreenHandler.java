package com.easyim.server.handler.biz;

import com.alibaba.fastjson.JSON;
import com.easyim.comm.message.screen.ShareScreenRequestMessage;
import com.easyim.comm.message.screen.ShareScreenResponseMessage;
import com.easyim.common.Constants;
import com.easyim.common.ServerSessionUtil;
import com.easyim.pojo.MeetingDO;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;

/**
 * 屏幕共享消息处理器
 *
 * @author 单程车票
 */
@Slf4j
@ChannelHandler.Sharable
public class ShareScreenHandler extends SimpleChannelInboundHandler<ShareScreenRequestMessage> {

    /**
     * 静态内部类（单例模式）
     */
    private static class ShareScreenHandlerInstance {
        private static final ShareScreenHandler INSTANCE = new ShareScreenHandler();
    }

    /**
     * 获取单例模式下的实例
     */
    public static ShareScreenHandler getInstance() {
        return ShareScreenHandlerInstance.INSTANCE;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ShareScreenRequestMessage msg) throws Exception {
        log.debug("屏幕共享消息处理请求：{}", JSON.toJSONString(msg));
        // 获取通道信息
        String meetingId = (String) ctx.channel().attr(AttributeKey.valueOf(Constants.AttributeKeyName.MEETING_ID)).get();
        String nickname = (String) ctx.channel().attr(AttributeKey.valueOf(Constants.AttributeKeyName.USER_NAME)).get();
        // 获取会议信息
        MeetingDO meeting = ServerSessionUtil.getMeeting(meetingId);
        // 推送响应
        ChannelGroup channelGroup = meeting.getChannelGroup();
        ShareScreenResponseMessage message = new ShareScreenResponseMessage(msg.getMessageId(), meeting.getTheme(), nickname, meetingId, false);
        for (Channel channel : channelGroup) {
            if (channel.id().equals(ctx.channel().id())) {
                channel.writeAndFlush(new ShareScreenResponseMessage(msg.getMessageId(), meeting.getTheme(), nickname, meetingId, true));
            } else {
                channel.writeAndFlush(message);
            }
        }
    }

}
