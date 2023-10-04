package com.easyim.server.handler;

import com.alibaba.fastjson.JSON;
import com.easyim.comm.message.chat.ChatRequestMessage;
import com.easyim.comm.message.chat.ChatResponseMessage;
import com.easyim.dal.dataobject.DialogDO;
import com.easyim.server.common.ServerChannelUtil;
import com.easyim.server.common.ServerConstants;
import com.easyim.service.DialogService;
import com.easyim.service.RecordService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 聊天处理器
 *
 * @author 单程车票
 */
@Slf4j
@Component
@ChannelHandler.Sharable
public class ChatHandler extends SimpleChannelInboundHandler<ChatRequestMessage> {

    @Autowired
    private DialogService dialogService;

    @Autowired
    private RecordService recordService;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ChatRequestMessage msg) throws Exception {
        log.info("聊天消息处理请求：{}", JSON.toJSONString(msg));
        // 获取对应的对话信息
        DialogDO dialog = dialogService.queryDialog(msg.getSenderId(), msg.getReceiverId());
        // 异步消息落库
        recordService.saveRecord(dialog.getDialogId(), msg.getSenderId(), msg.getContent());
        // 根据对话信息判断是群聊还是单聊
        if (Objects.equals(ServerConstants.DialogType.SINGLE_CHAT.getCode(), dialog.getDialogType())) {
            // 推送好友
            Channel friendChannel = ServerChannelUtil.getChannel(msg.getReceiverId());
            if (friendChannel == null) {
                // TODO 返回错误结果
                log.info("用户 {} 处于离线状态", msg.getReceiverId());
            } else {
                friendChannel.writeAndFlush(new ChatResponseMessage(msg.getSenderId(), msg.getReceiverId(), msg.getContent(), msg.getNow()));
            }
        } else {
            // 推送群组
            ChannelGroup groupChannel = ServerChannelUtil.getGroup(msg.getReceiverId());
            if (groupChannel == null) {

            } else {
                groupChannel.writeAndFlush(new ChatResponseMessage(msg.getSenderId(), msg.getReceiverId(), msg.getContent(), msg.getNow()));
            }
        }
    }

}
