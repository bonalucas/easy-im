package com.easyim.comm.message.meeting;

import com.easyim.comm.message.Message;
import com.easyim.comm.message.MessageTypeConstants;

/**
 * 创建会议请求消息
 *
 * @author 单程车票
 */
public class CreateMeetingRequestMessage extends Message {

    /**
     * 会议ID
     */
    private String meetingId;

    /**
     * 会议主题
     */
    private String theme;

    /**
     * 入会昵称
     */
    private String nickname;

    public CreateMeetingRequestMessage() {
    }

    public CreateMeetingRequestMessage(String meetingId, String theme, String nickname) {
        this.meetingId = meetingId;
        this.theme = theme;
        this.nickname = nickname;
    }

    public CreateMeetingRequestMessage(long messageId) {
        super.setMessageId(messageId);
    }

    public CreateMeetingRequestMessage(long messageId, String meetingId, String theme, String nickname) {
        super.setMessageId(messageId);
        this.meetingId = meetingId;
        this.theme = theme;
        this.nickname = nickname;
    }

    public String getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(String meetingId) {
        this.meetingId = meetingId;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public Byte getConstant() {
        return MessageTypeConstants.CreateMeetingRequestMessage;
    }

}
