package com.easyim.comm.message.test;

import com.easyim.comm.message.Message;
import com.easyim.comm.message.MessageTypeConstants;

public class TestResponseMessage extends Message {

    private String content;

    public TestResponseMessage() {
    }

    public TestResponseMessage(String content) {
        this.content = content;
    }

    public TestResponseMessage(long messageId) {
        super.setMessageId(messageId);
    }

    public TestResponseMessage(long messageId, String content) {
        super.setMessageId(messageId);
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public Byte getConstant() {
        return MessageTypeConstants.TestResponseMessage;
    }

}
