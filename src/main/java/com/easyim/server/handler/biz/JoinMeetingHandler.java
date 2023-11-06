package com.easyim.server.handler.biz;

import com.alibaba.fastjson.JSON;
import com.easyim.comm.message.meeting.JoinMeetingRequestMessage;
import com.easyim.comm.message.meeting.JoinMeetingResponseMessage;
import com.easyim.common.Constants;
import com.easyim.common.EasyIMException;
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
 * 加入会议消息处理器
 *
 * @author 单程车票
 */
@Slf4j
@ChannelHandler.Sharable
public class JoinMeetingHandler extends SimpleChannelInboundHandler<JoinMeetingRequestMessage> {

    /**
     * 静态内部类（单例模式）
     */
    private static class JoinMeetingHandlerInstance {
        private static final JoinMeetingHandler INSTANCE = new JoinMeetingHandler();
    }

    /**
     * 获取单例模式下的实例
     */
    public static JoinMeetingHandler getInstance() {
        return JoinMeetingHandlerInstance.INSTANCE;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, JoinMeetingRequestMessage msg) throws Exception {
        log.debug("加入会议消息处理请求：{}", JSON.toJSONString(msg));
        // 记录通道属性
        ctx.channel().attr(AttributeKey.valueOf(Constants.AttributeKeyName.USER_NAME)).set(msg.getNickname());
        ctx.channel().attr(AttributeKey.valueOf(Constants.AttributeKeyName.MEETING_ID)).set(msg.getMeetingId());
        // 校验会议存在，加入会议
        if (!ServerSessionUtil.isExist(msg.getMeetingId())) throw new EasyIMException(msg.getMessageId(), Constants.EasyIMError.MEETING_NO_EXIST);
        ServerSessionUtil.addMeeting(msg.getMeetingId(), ctx.channel());
        // 获取会议主题
        MeetingDO meeting = ServerSessionUtil.getMeeting(msg.getMeetingId());
        // 通知会议所有人
        JoinMeetingResponseMessage message = new JoinMeetingResponseMessage(msg.getMessageId(), msg.getMeetingId(), meeting.getTheme(), msg.getNickname());
        ChannelGroup channelGroup = meeting.getChannelGroup();
        for (Channel channel : channelGroup) {
            channel.writeAndFlush(message);
        }
    }

}
