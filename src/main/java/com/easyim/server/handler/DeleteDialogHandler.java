package com.easyim.server.handler;

import com.alibaba.fastjson2.JSON;
import com.easyim.comm.message.dialog.DeleteDialogRequestMessage;
import com.easyim.service.DialogService;
import io.netty.channel.Channel;
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
public class DeleteDialogHandler extends BaseHandler<DeleteDialogRequestMessage> {

    @Autowired
    private DialogService dialogService;

    @Override
    public void channelRead(Channel channel, DeleteDialogRequestMessage msg) {
        log.info("接收删除对话请求：{}", JSON.toJSONString(msg));
        dialogService.deleteDialog(msg.getDialogId());
    }
}
