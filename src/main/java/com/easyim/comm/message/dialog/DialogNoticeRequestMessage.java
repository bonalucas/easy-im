package com.easyim.comm.message.dialog;

import com.easyim.comm.message.Message;
import com.easyim.comm.message.MessageTypeConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 对话请求消息
 *
 * @author 单程车票
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class DialogNoticeRequestMessage extends Message {

    /**
     * 发送者ID
     */
    private String senderId;

    /**
     * 接收者ID
     */
    private String receiverId;

    /**
     * 对话类型
     */
    private Integer dialogType;

    public DialogNoticeRequestMessage(String messageId, Boolean status) {
        super.setMessageId(messageId);
        super.setStatus(status);
    }

    public DialogNoticeRequestMessage(String messageId, Boolean status, String senderId, String receiverId, Integer dialogType) {
        super.setMessageId(messageId);
        super.setStatus(status);
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.dialogType = dialogType;
    }

    @Override
    public Byte getConstant() {
        return MessageTypeConstants.DialogNoticeRequestMessage;
    }

}
