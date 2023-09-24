package com.easyim.server.handler;

import com.alibaba.fastjson.JSON;
import com.easyim.comm.message.friend.AddFriendRequestMessage;
import com.easyim.comm.message.friend.AddFriendResponseMessage;
import com.easyim.common.ServiceException;
import com.easyim.dal.dataobject.UserDO;
import com.easyim.server.util.SocketChannelUtil;
import com.easyim.service.FriendService;
import com.easyim.service.UserService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
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
public class AddFriendHandler extends BaseHandler<AddFriendRequestMessage> {

    @Autowired
    private FriendService friendService;

    @Autowired
    private UserService userService;

    @Override
    public void channelRead(Channel channel, AddFriendRequestMessage msg) {
        log.info("添加好友消息处理请求：{}", JSON.toJSONString(msg));
        // 添加好友
        friendService.saveFriend(msg.getUserId(), msg.getFriendId());
        // 推送回用户
        UserDO friend = userService.queryUser(msg.getFriendId());
        channel.writeAndFlush(new AddFriendResponseMessage(friend.getUserId(), friend.getUserNickname(), friend.getUserAvatar()));
        // 推送回好友
        Channel friendChannel = SocketChannelUtil.getChannel(msg.getFriendId());
        if (friendChannel == null) throw new ServiceException("用户处于离线状态");
        UserDO user = userService.queryUser(msg.getUserId());
        friendChannel.writeAndFlush(new AddFriendResponseMessage(user.getUserId(), user.getUserNickname(), user.getUserAvatar()));
    }

}
