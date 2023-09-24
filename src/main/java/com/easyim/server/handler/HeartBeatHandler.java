package com.easyim.server.handler;

import com.easyim.comm.message.heartbeat.HeartBeatRequestMessage;
import com.easyim.comm.message.heartbeat.HeartBeatResponseMessage;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 心跳包处理器
 *
 * @author 单程车票
 */
@Slf4j
@Component
@ChannelHandler.Sharable
public class HeartBeatHandler extends BaseHandler<HeartBeatRequestMessage> {

    @Override
    public void channelRead(Channel channel, HeartBeatRequestMessage msg) {
        // 收到客户端的心跳包后发送响应心跳包
        channel.writeAndFlush(new HeartBeatResponseMessage());
    }

}
