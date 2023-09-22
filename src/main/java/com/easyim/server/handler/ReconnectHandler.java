package com.easyim.server.handler;

import com.alibaba.fastjson.JSON;
import com.easyim.comm.message.reconnect.ReconnectRequestMessage;
import com.easyim.server.util.SocketChannelUtil;
import com.easyim.service.MemberService;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 断线重连处理器
 *
 * @author 单程车票
 */
@Slf4j
@Component
public class ReconnectHandler extends BaseHandler<ReconnectRequestMessage> {

    @Autowired
    private MemberService memberService;

    @Override
    public void channelRead(Channel channel, ReconnectRequestMessage msg) {
        log.info("断线重连消息处理请求：{}", JSON.toJSONString(msg));
        // 重新添加 channel
        SocketChannelUtil.addChannel(msg.getUserId(), channel);
        // 重新添加群组
        List<String> groupIds = memberService.queryGroup(msg.getUserId());
        for (String groupId : groupIds) {
            SocketChannelUtil.addGroup(groupId, channel);
        }
    }
}
