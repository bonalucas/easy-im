package com.easyim.server.handler;

import com.alibaba.fastjson.JSON;
import com.easyim.comm.message.test.TestRequestMessage;
import com.easyim.comm.message.test.TestResponseMessage;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@ChannelHandler.Sharable
public class TestMessageHandler extends SimpleChannelInboundHandler<TestRequestMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TestRequestMessage msg) throws Exception {
        log.info("测试消息处理请求：{}", JSON.toJSONString(msg));
        TestResponseMessage responseMessage = new TestResponseMessage(msg.getMessageId(), true, msg.getContent());
        ctx.writeAndFlush(responseMessage);
    }

}
