package com.easyim.server.handler.biz;

import com.alibaba.fastjson.JSON;
import com.easyim.comm.message.chat.ChatRequestMessage;
import com.easyim.comm.message.chat.ChatResponseMessage;
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
 * 聊天处理器
 *
 * @author 单程车票
 */
@Slf4j
@ChannelHandler.Sharable
public class ChatHandler extends SimpleChannelInboundHandler<ChatRequestMessage> {

    /**
     * 静态内部类（单例模式）
     */
    private static class ChatHandlerInstance {
        private static final ChatHandler INSTANCE = new ChatHandler();
    }

    /**
     * 获取单例模式下的实例
     */
    public static ChatHandler getInstance() {
        return ChatHandlerInstance.INSTANCE;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ChatRequestMessage msg) throws Exception {
        log.debug("聊天消息处理请求：{}", JSON.toJSONString(msg));
        // 获取通道信息
        String meetingId = (String) ctx.channel().attr(AttributeKey.valueOf(Constants.AttributeKeyName.MEETING_ID)).get();
        String nickname = (String) ctx.channel().attr(AttributeKey.valueOf(Constants.AttributeKeyName.USER_NAME)).get();
        // 通知所有人聊天消息
        ChannelGroup channelGroup = ServerSessionUtil.getMeetingChannel(meetingId);
        if (channelGroup == null) throw new EasyIMException(msg.getMessageId(), Constants.EasyIMError.MEETING_NO_EXIST);
        ChatResponseMessage message = new ChatResponseMessage(msg.getMessageId(), nickname, msg.getType(), msg.getContent());
        for (Channel channel : channelGroup) {
            channel.writeAndFlush(message);
        }
    }

}
