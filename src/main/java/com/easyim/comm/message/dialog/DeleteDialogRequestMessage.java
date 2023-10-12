package com.easyim.comm.message.dialog;

import com.easyim.comm.message.Message;
import com.easyim.comm.message.MessageTypeConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 删除对话请求消息
 *
 * @author 单程车票
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class DeleteDialogRequestMessage extends Message {

    /**
     * 对话ID
     */
    private String dialogId;

    /**
     * 发送者ID
     */
    private String senderId;

    public DeleteDialogRequestMessage(String messageId, Boolean status) {
        super.setMessageId(messageId);
        super.setStatus(status);
    }

    public DeleteDialogRequestMessage(String messageId, Boolean status, String dialogId, String senderId) {
        super.setMessageId(messageId);
        super.setStatus(status);
        this.dialogId = dialogId;
        this.senderId = senderId;
    }

    @Override
    public Byte getConstant() {
        return MessageTypeConstants.DeleteDialogRequestMessage;
    }

}
