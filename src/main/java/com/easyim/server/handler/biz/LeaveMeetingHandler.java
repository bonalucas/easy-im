package com.easyim.server.handler.biz;

import com.alibaba.fastjson.JSON;
import com.easyim.comm.message.meeting.LeaveMeetingRequestMessage;
import com.easyim.comm.message.meeting.LeaveMeetingResponseMessage;
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
 * 退出会议处理器
 *
 * @author 单程车票
 */
@Slf4j
@ChannelHandler.Sharable
public class LeaveMeetingHandler extends SimpleChannelInboundHandler<LeaveMeetingRequestMessage> {

    /**
     * 静态内部类（单例模式）
     */
    private static class LeaveMeetingHandlerInstance {
        private static final LeaveMeetingHandler INSTANCE = new LeaveMeetingHandler();
    }

    /**
     * 获取单例模式下的实例
     */
    public static LeaveMeetingHandler getInstance() {
        return LeaveMeetingHandlerInstance.INSTANCE;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LeaveMeetingRequestMessage msg) throws Exception {
        log.debug("退出会议消息处理请求：{}", JSON.toJSONString(msg));
        // 获取通道属性
        String meetingId = (String) ctx.channel().attr(AttributeKey.valueOf(Constants.AttributeKeyName.MEETING_ID)).get();
        String nickname = (String) ctx.channel().attr(AttributeKey.valueOf(Constants.AttributeKeyName.USER_NAME)).get();
        if (meetingId == null || nickname == null) throw new EasyIMException(msg.getMessageId(), Constants.EasyIMError.USER_INFO_ERROR);
        // 清除通道属性
        ctx.channel().attr(AttributeKey.valueOf(Constants.AttributeKeyName.USER_NAME)).set(null);
        ctx.channel().attr(AttributeKey.valueOf(Constants.AttributeKeyName.MEETING_ID)).set(null);
        // 校验会议是否存在
        if (!ServerSessionUtil.isExist(meetingId)) throw new EasyIMException(msg.getMessageId(), Constants.EasyIMError.MEETING_NO_EXIST);
        // 退出会议缓存
        ServerSessionUtil.leaveMeeting(meetingId, ctx.channel());
        // 获取会议通道
        ChannelGroup channelGroup = ServerSessionUtil.getMeetingChannel(meetingId);
        if (channelGroup == null) throw new EasyIMException(msg.getMessageId(), Constants.EasyIMError.MEETING_NO_EXIST);
        // 通知会议其他用户
        LeaveMeetingResponseMessage message = new LeaveMeetingResponseMessage(nickname);
        for (Channel channel : channelGroup) {
            channel.writeAndFlush(message);
        }
    }

}
