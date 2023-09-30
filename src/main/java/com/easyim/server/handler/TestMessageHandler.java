package com.easyim.server.handler;

import com.alibaba.fastjson.JSON;
import com.easyim.comm.message.test.TestRequestMessage;
import com.easyim.comm.message.test.TestResponseMessage;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@ChannelHandler.Sharable
public class TestMessageHandler extends BaseHandler<TestRequestMessage>{

    @Override
    public void channelRead(Channel channel, TestRequestMessage msg) {
        log.info("测试消息处理请求：{}", JSON.toJSONString(msg));
        TestResponseMessage responseMessage = new TestResponseMessage(msg.getContent());
        channel.writeAndFlush(responseMessage);
    }
}
