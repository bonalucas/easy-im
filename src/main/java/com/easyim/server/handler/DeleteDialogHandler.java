package com.easyim.server.handler;

import com.alibaba.fastjson2.JSON;
import com.easyim.comm.message.dialog.DeleteDialogRequestMessage;
import com.easyim.service.DialogService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 删除对话处理器
 *
 * @author 单程车票
 */
@Slf4j
@Component
@ChannelHandler.Sharable
public class DeleteDialogHandler extends BaseHandler<DeleteDialogRequestMessage> {

    @Autowired
    private DialogService dialogService;

    @Override
    public void channelRead(Channel channel, DeleteDialogRequestMessage msg) {
        log.info("删除对话消息处理请求：{}", JSON.toJSONString(msg));
        dialogService.deleteDialog(msg.getDialogId());
    }
}
