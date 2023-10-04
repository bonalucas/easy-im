package com.easyim.server.handler;

import com.alibaba.fastjson.JSON;
import com.easyim.comm.message.friend.SearchFriendRequestMessage;
import com.easyim.comm.message.friend.SearchFriendResponseMessage;
import com.easyim.comm.message.friend.dto.UserDto;
import com.easyim.convert.UserConvert;
import com.easyim.dal.dataobject.UserDO;
import com.easyim.server.common.ServerConstants;
import com.easyim.service.FriendService;
import com.easyim.service.UserService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 查找好友处理器
 *
 * @author 单程车票
 */
@Slf4j
@Component
@ChannelHandler.Sharable
public class SearchFriendHandler extends SimpleChannelInboundHandler<SearchFriendRequestMessage> {

    @Autowired
    private UserService userService;

    @Autowired
    private FriendService friendService;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, SearchFriendRequestMessage msg) throws Exception {
        // TODO 后期优化 - 创建添加好友申请表（目前只要有一方添加好友则可添加成功，无需等待对方同意）
        log.info("查找好友消息处理请求：{}", JSON.toJSONString(msg));
        List<UserDto> userDtoList = new ArrayList<>();
        List<UserDO> userDOList = userService.queryUserByNickname(msg.getSearchKey());
        Set<String> existFriend = new HashSet<>(friendService.queryFriendList(msg.getUserId()));
        for (UserDO userDO : userDOList) {
            UserDto userDto = UserConvert.INSTANCT.convertDto(userDO);
            if (existFriend.contains(userDO.getUserId())) {
                userDto.setStatus(ServerConstants.UserStatus.ADDED.getCode());
            } else {
                userDto.setStatus(ServerConstants.UserStatus.UN_ADD.getCode());
            }
        }
        SearchFriendResponseMessage response = new SearchFriendResponseMessage();
        response.setList(userDtoList);
        ctx.writeAndFlush(response);
    }

}
