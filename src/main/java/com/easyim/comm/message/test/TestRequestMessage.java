package com.easyim.comm.message.test;

import com.easyim.comm.message.Message;
import com.easyim.comm.message.MessageTypeConstants;

public class TestRequestMessage extends Message {

    private String content;

    public TestRequestMessage(long messageId) {
        super.setMessageId(messageId);
    }

    public TestRequestMessage(long messageId, String content) {
        super.setMessageId(messageId);
        this.content = content;
    }

    @Override
    public Byte getConstant() {
        return MessageTypeConstants.TestRequestMessage;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
