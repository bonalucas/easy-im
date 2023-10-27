package com.easyim.comm.server.handler;

import com.alibaba.fastjson.JSON;
import com.easyim.comm.message.file.FileUploadRequestMessage;
import com.easyim.comm.message.file.FileUploadResponseMessage;
import com.easyim.comm.server.common.SnowflakeIDGenerator;
import com.easyim.service.FileService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 文件上传处理器
 *
 * @author 单程车票
 */
@Slf4j
@Component
@ChannelHandler.Sharable
public class FileUploadHandler extends SimpleChannelInboundHandler<FileUploadRequestMessage> {

    @Autowired
    private FileService fileService;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FileUploadRequestMessage msg) throws Exception {
        log.info("文件上传消息处理请求：{}", JSON.toJSONString(msg));
        // 上传文件并获取文件访问地址
        String fileUrl;
        try {
            fileUrl = fileService.uploadFile(msg.getFileName(), msg.getFileSize(), msg.getFileContent());
        } catch (Exception e) {
            // TODO 响应
            fileUrl = null;
            log.error("文件上传失败");
        }
        // 推送回客户端文件访问地址
        ctx.writeAndFlush(new FileUploadResponseMessage(SnowflakeIDGenerator.generateID(), fileUrl));
    }

}
