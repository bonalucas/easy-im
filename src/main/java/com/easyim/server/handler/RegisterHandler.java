package com.easyim.server.handler;

import com.alibaba.fastjson.JSON;
import com.easyim.comm.message.register.RegisterRequestMessage;
import com.easyim.comm.message.register.RegisterResponseMessage;
import com.easyim.service.UserService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 注册处理器
 *
 * @author 单程车票
 */
@Slf4j
@Component
@ChannelHandler.Sharable
public class RegisterHandler extends SimpleChannelInboundHandler<RegisterRequestMessage> {

    @Autowired
    private UserService userService;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RegisterRequestMessage msg) throws Exception {
        log.info("注册消息处理请求：{}", JSON.toJSONString(msg));
        boolean res = userService.register(msg.getUsername(), msg.getPassword(), msg.getNickname(), msg.getAvatar());
        RegisterResponseMessage message = new RegisterResponseMessage();
        if (res) {
            message.setResponseMsg("注册成功");
            message.setServerStatus(true);
            ctx.writeAndFlush(message);
        } else {
            message.setResponseMsg("注册失败");
            message.setServerStatus(false);
            ctx.writeAndFlush(message);
        }
    }

}
