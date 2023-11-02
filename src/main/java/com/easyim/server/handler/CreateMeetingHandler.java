package com.easyim.server.handler;

import com.alibaba.fastjson.JSON;
import com.easyim.comm.message.meeting.MeetingCreateRequestMessage;
import com.easyim.comm.message.meeting.MeetingCreateResponseMessage;
import com.easyim.common.Constants;
import com.easyim.common.EasyIMException;
import com.easyim.common.ServerSessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;

/**
 * 创建会议消息处理器
 *
 * @author 单程车票
 */
@Slf4j
@ChannelHandler.Sharable
public class CreateMeetingHandler extends SimpleChannelInboundHandler<MeetingCreateRequestMessage> {

    /**
     * 静态内部类（单例模式）
     */
    private static class CreateMeetingHandlerInstance {
        private static final CreateMeetingHandler INSTANCE = new CreateMeetingHandler();
    }

    /**
     * 获取单例模式下的实例
     */
    public static CreateMeetingHandler getInstance() {
        return CreateMeetingHandlerInstance.INSTANCE;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MeetingCreateRequestMessage msg) throws Exception {
        log.debug("创建会议消息处理请求：{}", JSON.toJSONString(msg));
        // 记录通道属性
        ctx.channel().attr(AttributeKey.valueOf(Constants.AttributeKeyName.USER_NAME)).set(msg.getCreator());
        ctx.channel().attr(AttributeKey.valueOf(Constants.AttributeKeyName.MEETING_ID)).set(msg.getMeetingID());
        // 记录会议缓存
        if (ServerSessionUtil.isExist(msg.getMeetingID())) throw new EasyIMException(msg.getMessageId(), Constants.EasyIMError.MEETING_CONFLICT);
        ServerSessionUtil.createMeeting(msg.getMeetingID(), msg.getTheme(), ctx.channel());
        // 返回响应
        ctx.writeAndFlush(new MeetingCreateResponseMessage(msg.getMessageId(), msg.getMeetingID(), msg.getCreator(), msg.getTheme()));
    }

}
