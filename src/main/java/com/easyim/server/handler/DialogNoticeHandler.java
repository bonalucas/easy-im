package com.easyim.server.handler;

import com.alibaba.fastjson.JSON;
import com.easyim.comm.message.dialog.DialogNoticeRequestMessage;
import com.easyim.comm.message.dialog.DialogNoticeResponseMessage;
import com.easyim.common.Constants;
import com.easyim.dal.dataobject.DialogDO;
import com.easyim.dal.dataobject.UserDO;
import com.easyim.server.util.SocketChannelUtil;
import com.easyim.service.DialogService;
import com.easyim.service.UserService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 对话通知处理器
 *
 * @author 单程车票
 */
@Slf4j
@Component
@ChannelHandler.Sharable
public class DialogNoticeHandler extends BaseHandler<DialogNoticeRequestMessage> {

    @Autowired
    private DialogService dialogService;

    @Autowired
    private UserService userService;

    @Override
    public void channelRead(Channel channel, DialogNoticeRequestMessage msg) {
        log.info("对话通知消息处理请求：{}", JSON.toJSONString(msg));
        if (Objects.equals(Constants.DialogType.SINGLE_CHAT.getCode(), msg.getDialogType())) {
            // 消息落库
            dialogService.createSingleChatDialog(msg.getSenderId(), msg.getReceiverId());
            // 查询对话ID
            DialogDO dialog = dialogService.queryDialog(msg.getSenderId(), msg.getReceiverId());
            UserDO user = userService.queryUser(msg.getReceiverId());
            // 构造响应结果
            DialogNoticeResponseMessage response = new DialogNoticeResponseMessage();
            response.setDialogId(dialog.getDialogId());
            response.setName(user.getUserNickname());
            response.setAvatar(user.getUserAvatar());
            response.setSketch(null);
            response.setNow(null);
            // 获取好友通信管道
            Channel friendChannel = SocketChannelUtil.getChannel(msg.getReceiverId());
            if (null == friendChannel) {
                log.info("用户 {} 处于离线状态", msg.getReceiverId());
                return;
            }
            friendChannel.writeAndFlush(response);
        } else {
            dialogService.createGroupChatDialog(msg.getSenderId(), msg.getReceiverId());
        }
    }

}
