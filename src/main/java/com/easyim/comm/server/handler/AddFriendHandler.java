package com.easyim.comm.server.handler;

import com.alibaba.fastjson.JSON;
import com.easyim.comm.message.friend.AddFriendRequestMessage;
import com.easyim.comm.message.friend.AddFriendResponseMessage;
import com.easyim.comm.server.common.ServerChannelUtil;
import com.easyim.comm.server.common.SnowflakeIDGenerator;
import com.easyim.dal.dataobject.UserDO;
import com.easyim.service.FriendService;
import com.easyim.service.UserService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 添加好友处理器
 *
 * @author 单程车票
 */
@Slf4j
@Component
@ChannelHandler.Sharable
public class AddFriendHandler extends SimpleChannelInboundHandler<AddFriendRequestMessage> {

    @Autowired
    private FriendService friendService;

    @Autowired
    private UserService userService;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, AddFriendRequestMessage msg) throws Exception {
        log.info("添加好友消息处理请求：{}", JSON.toJSONString(msg));
        // 添加好友
        friendService.saveFriend(msg.getUserId(), msg.getFriendId());
        // 推送回用户
        UserDO friend = userService.queryUser(msg.getFriendId());
        ctx.writeAndFlush(new AddFriendResponseMessage(SnowflakeIDGenerator.generateID(), friend.getUserId(), friend.getUserNickname(), friend.getUserAvatar()));
        // 推送回好友
        Channel friendChannel = ServerChannelUtil.getChannel(msg.getFriendId());
        if (friendChannel == null) {
            // TODO 响应
            log.error("用户不存在");
            return;
        }
        UserDO user = userService.queryUser(msg.getUserId());
        friendChannel.writeAndFlush(new AddFriendResponseMessage(SnowflakeIDGenerator.generateID(), user.getUserId(), user.getUserNickname(), user.getUserAvatar()));
    }

}
