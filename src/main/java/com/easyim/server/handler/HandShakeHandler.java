package com.easyim.server.handler;

import com.alibaba.fastjson.JSON;
import com.easyim.comm.message.handshake.HandShakeRequestMessage;
import com.easyim.comm.message.handshake.HandShakeResponseMessage;
import com.easyim.comm.message.meeting.LeaveMeetingResponseMessage;
import com.easyim.common.Constants;
import com.easyim.common.ServerSessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 握手处理器（用于处理通道连接与通道关闭事件）
 *
 * @author 单程车票
 */
@Slf4j
@ChannelHandler.Sharable
public class HandShakeHandler extends SimpleChannelInboundHandler<HandShakeRequestMessage> {

    /**
     * 客户端地址缓存表
     */
    private final Map<String, Boolean> nodeCache = new ConcurrentHashMap<>();

    /**
     * 静态内部类（单例模式）
     */
    private static class HandShakeHandlerInstance {
        private static final HandShakeHandler INSTANCE = new HandShakeHandler();
    }

    /**
     * 获取单例模式下的实例
     */
    public static HandShakeHandler getInstance() {
        return HandShakeHandlerInstance.INSTANCE;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        log.debug("客户端 【{}】 建立连接", ctx.channel().remoteAddress());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        // 移除会议缓存信息
        String meetingID = (String) ctx.channel().attr(AttributeKey.valueOf(Constants.AttributeKeyName.MEETING_ID)).get();
        String nickname = (String) ctx.channel().attr(AttributeKey.valueOf(Constants.AttributeKeyName.USER_NAME)).get();
        if (StringUtils.isNotEmpty(meetingID)) {
            // 移除会议缓存
            ServerSessionUtil.leaveMeeting(meetingID, ctx.channel());
            // 告知其他用户
            ChannelGroup channelGroup = ServerSessionUtil.getMeetingChannel(meetingID);
            assert channelGroup != null;
            for (Channel channel : channelGroup) {
                channel.writeAndFlush(new LeaveMeetingResponseMessage(nickname));
            }
        }
        log.debug("客户端 【{}】 断开连接", ctx.channel().remoteAddress());
        ctx.close();
        nodeCache.remove(ctx.channel().remoteAddress().toString());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HandShakeRequestMessage msg) throws Exception {
        log.debug("握手消息处理请求：{}", JSON.toJSONString(msg));
        String node = ctx.channel().remoteAddress().toString();
        // 校验远程节点重复连接
        if (nodeCache.containsKey(node)) {
            ctx.writeAndFlush(new HandShakeResponseMessage(Boolean.FALSE));
        } else {
            nodeCache.put(node, Boolean.TRUE);
            ctx.writeAndFlush(new HandShakeResponseMessage(Boolean.TRUE));
        }
    }

}
