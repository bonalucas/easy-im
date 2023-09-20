package com.easyim.server.handler;

import com.easyim.comm.message.file.FileUploadRequestMessage;
import com.easyim.comm.message.file.FileUploadResponseMessage;
import com.easyim.service.FileService;
import io.netty.channel.Channel;
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
public class FileUploadHandler extends BaseHandler<FileUploadRequestMessage> {

    @Autowired
    private FileService fileService;

    @Override
    public void channelRead(Channel channel, FileUploadRequestMessage msg) {
        // 上传文件并获取文件访问地址
        String fileUrl = fileService.uploadFile(msg.getFileName(), msg.getFileSize(), msg.getFileContent());
        // 推送回客户端文件访问地址
        channel.writeAndFlush(new FileUploadResponseMessage(fileUrl));
    }

}
