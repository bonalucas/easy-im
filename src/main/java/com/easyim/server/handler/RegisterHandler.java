package com.easyim.server.handler;

import com.alibaba.fastjson.JSON;
import com.easyim.comm.message.register.RegisterRequestMessage;
import com.easyim.comm.message.register.RegisterResponseMessage;
import com.easyim.common.ServiceException;
import com.easyim.service.UserService;
import io.netty.channel.Channel;
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
public class RegisterHandler extends BaseHandler<RegisterRequestMessage> {

    @Autowired
    private UserService userService;

    @Override
    public void channelRead(Channel channel, RegisterRequestMessage msg) {
        log.info("注册消息处理请求：{}", JSON.toJSONString(msg));
        boolean res = userService.register(msg.getUsername(), msg.getPassword(), msg.getNickname(), msg.getAvatar());
        if (res) {
            channel.writeAndFlush(new RegisterResponseMessage("注册成功"));
        } else {
          throw new ServiceException("注册失败");
        }
    }

}
