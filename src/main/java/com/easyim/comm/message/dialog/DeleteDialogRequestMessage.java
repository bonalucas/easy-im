package com.easyim.comm.message.dialog;

import com.easyim.comm.message.Message;
import com.easyim.comm.message.MessageTypeConstants;

/**
 * 删除对话请求消息
 *
 * @author 单程车票
 */
public class DeleteDialogRequestMessage extends Message {

    /**
     * 对话ID
     */
    private String dialogId;

    /**
     * 发送者ID
     */
    private String senderId;

    public DeleteDialogRequestMessage(long messageId) {
        super.setMessageId(messageId);
    }

    public DeleteDialogRequestMessage(long messageId, String dialogId, String senderId) {
        super.setMessageId(messageId);
        this.dialogId = dialogId;
        this.senderId = senderId;
    }

    public String getDialogId() {
        return dialogId;
    }

    public void setDialogId(String dialogId) {
        this.dialogId = dialogId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    @Override
    public Byte getConstant() {
        return MessageTypeConstants.DeleteDialogRequestMessage;
    }

}
