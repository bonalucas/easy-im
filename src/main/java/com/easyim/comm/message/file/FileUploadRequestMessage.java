package com.easyim.comm.message.file;

import com.easyim.comm.message.Message;
import com.easyim.comm.message.MessageTypeConstants;

/**
 * 文件上传请求消息
 *
 * @author 单程车票
 */
public class FileUploadRequestMessage extends Message {

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件大小
     */
    private long fileSize;

    /**
     * 文件内容
     */
    private byte[] fileContent;

    public FileUploadRequestMessage(long messageId) {
        super.setMessageId(messageId);
    }

    public FileUploadRequestMessage(long messageId, String fileName, long fileSize, byte[] fileContent) {
        super.setMessageId(messageId);
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.fileContent = fileContent;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public byte[] getFileContent() {
        return fileContent;
    }

    public void setFileContent(byte[] fileContent) {
        this.fileContent = fileContent;
    }

    @Override
    public Byte getConstant() {
        return MessageTypeConstants.FileUploadRequestMessage;
    }

}
