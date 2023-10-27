package com.easyim.comm.message.handshake;

import com.easyim.comm.message.Message;
import com.easyim.comm.message.MessageTypeConstants;

/**
 * 握手响应消息
 *
 * @author 单程车票
 */
public class HandShakeResponseMessage extends Message {

    public HandShakeResponseMessage(long messageId) {
        super.setMessageId(messageId);
    }

    @Override
    public Byte getConstant() {
        return MessageTypeConstants.HandShakeResponseMessage;
    }

}
