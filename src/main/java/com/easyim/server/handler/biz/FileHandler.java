package com.easyim.server.handler.biz;

import com.alibaba.fastjson.JSON;
import com.easyim.comm.message.file.FileRequestMessage;
import com.easyim.comm.message.file.FileResponseMessage;
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
 * 文件处理器
 *
 * @author 单程车票
 */
@Slf4j
@ChannelHandler.Sharable
public class FileHandler extends SimpleChannelInboundHandler<FileRequestMessage> {

    /**
     * 静态内部类（单例模式）
     */
    private static class FileHandlerInstance {
        private static final FileHandler INSTANCE = new FileHandler();
    }

    /**
     * 获取单例模式下的实例
     */
    public static FileHandler getInstance() {
        return FileHandlerInstance.INSTANCE;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FileRequestMessage msg) throws Exception {
        log.debug("文件消息处理请求：{}", JSON.toJSONString(msg));
        // 获取通道信息
        String meetingId = (String) ctx.channel().attr(AttributeKey.valueOf(Constants.AttributeKeyName.MEETING_ID)).get();
        String nickname = (String) ctx.channel().attr(AttributeKey.valueOf(Constants.AttributeKeyName.USER_NAME)).get();
        if (meetingId == null || nickname == null) throw new EasyIMException(msg.getMessageId(), Constants.EasyIMError.USER_INFO_ERROR);
        // 通知所有人聊天消息
        ChannelGroup channelGroup = ServerSessionUtil.getMeetingChannel(meetingId);
        if (channelGroup == null) throw new EasyIMException(msg.getMessageId(), Constants.EasyIMError.MEETING_NO_EXIST);
        FileResponseMessage message = new FileResponseMessage(nickname, msg.getFileName(), msg.getMimeType(), msg.getFile());
        for (Channel channel : channelGroup) {
            if (!channel.id().equals(ctx.channel().id())) channel.writeAndFlush(message);
        }
    }

}
