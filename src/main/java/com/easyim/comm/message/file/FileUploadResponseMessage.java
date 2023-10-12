package com.easyim.comm.message.file;

import com.easyim.comm.message.Message;
import com.easyim.comm.message.MessageTypeConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 文件上传响应消息
 *
 * @author 单程车票
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class FileUploadResponseMessage extends Message {

    /**
     * 文件访问地址
     */
    private String fileUrl;

    public FileUploadResponseMessage(String messageId, Boolean status) {
        super.setMessageId(messageId);
        super.setStatus(status);
    }

    public FileUploadResponseMessage(String messageId, Boolean status, String fileUrl) {
        super.setMessageId(messageId);
        super.setStatus(status);
        this.fileUrl = fileUrl;
    }

    @Override
    public Byte getConstant() {
        return MessageTypeConstants.FileUploadResponseMessage;
    }
}
