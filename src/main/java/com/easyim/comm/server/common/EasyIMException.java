package com.easyim.comm.server.common;

/**
 * Easy-IM 异常类
 *
 * @author 单程车票
 */
public class EasyIMException extends RuntimeException {

    /**
     * 异常消息的ID
     */
    private Long messageId;

    public EasyIMException(Long messageId, String message) {
        super(message);
        this.messageId = messageId;
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

}
