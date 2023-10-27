package com.easyim.comm.message.file;

import com.easyim.comm.message.Message;
import com.easyim.comm.message.MessageTypeConstants;

/**
 * 文件上传响应消息
 *
 * @author 单程车票
 */
public class FileUploadResponseMessage extends Message {

    /**
     * 文件访问地址
     */
    private String fileUrl;

    public FileUploadResponseMessage(long messageId) {
        super.setMessageId(messageId);
    }

    public FileUploadResponseMessage(long messageId, String fileUrl) {
        super.setMessageId(messageId);
        this.fileUrl = fileUrl;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    @Override
    public Byte getConstant() {
        return MessageTypeConstants.FileUploadResponseMessage;
    }

}
