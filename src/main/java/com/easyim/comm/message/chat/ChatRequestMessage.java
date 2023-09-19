package com.easyim.comm.message.chat;

import com.easyim.comm.message.Message;
import com.easyim.comm.message.MessageTypeConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 聊天请求信息
 *
 * @author 单程车票
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class ChatRequestMessage extends Message {

    /**
     * 发送者ID
     */
    private String senderId;

    /**
     * 接收者ID
     */
    private String receiverId;

    /**
     * 内容
     */
    private String content;

    /**
     * 发送时间
     */
    private Date now;

    @Override
    public Byte getConstant() {
        return MessageTypeConstants.ChatRequestMessage;
    }

}
