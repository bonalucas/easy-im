package com.easyim.comm.message.dialog;

import com.easyim.comm.message.Message;
import com.easyim.comm.message.MessageTypeConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 对话请求消息
 *
 * @author 单程车票
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class DialogNoticeResponseMessage extends Message {

    /**
     * 对话ID
     */
    private String dialogId;

    /**
     * 接收者名称
     */
    private String name;

    /**
     * 接收者头像
     */
    private String avatar;

    /**
     * 对话简讯
     */
    private String sketch;

    /**
     * 对话最新消息时间
     */
    private Date now;

    public DialogNoticeResponseMessage(String messageId, Boolean status) {
        super.setMessageId(messageId);
        super.setStatus(status);
    }

    public DialogNoticeResponseMessage(String messageId, Boolean status, String dialogId, String name, String avatar, String sketch, Date now) {
        super.setMessageId(messageId);
        super.setStatus(status);
        this.dialogId = dialogId;
        this.name = name;
        this.avatar = avatar;
        this.sketch = sketch;
        this.now = now;
    }

    @Override
    public Byte getConstant() {
        return MessageTypeConstants.DialogNoticeResponseMessage;
    }

}
