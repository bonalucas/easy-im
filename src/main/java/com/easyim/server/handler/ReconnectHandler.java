package com.easyim.server.handler;

import com.alibaba.fastjson.JSON;
import com.easyim.comm.message.reconnect.ReconnectRequestMessage;
import com.easyim.server.common.ServerChannelUtil;
import com.easyim.service.MemberService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 断线重连处理器
 *
 * @author 单程车票
 */
@Slf4j
@Component
@ChannelHandler.Sharable
public class ReconnectHandler extends SimpleChannelInboundHandler<ReconnectRequestMessage> {

    @Autowired
    private MemberService memberService;

    @Autowired
    private ThreadPoolExecutor executor;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ReconnectRequestMessage msg) throws Exception {
        log.info("断线重连消息处理请求：{}", JSON.toJSONString(msg));
        // 重新添加 channel
        ServerChannelUtil.addChannel(msg.getUserId(), ctx.channel());
        // 重新添加群组
        List<String> groupIds = memberService.queryGroup(msg.getUserId());
        for (String groupId : groupIds) {
            executor.submit(() -> ServerChannelUtil.addGroup(groupId, ctx.channel()));
        }
    }

}
