package com.easyim.comm.message.friend;

import com.easyim.comm.message.Message;
import com.easyim.comm.message.MessageTypeConstants;

/**
 * 添加好友请求消息
 *
 * @author 单程车票
 */
public class AddFriendRequestMessage extends Message {

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 添加好友ID
     */
    private String friendId;

    public AddFriendRequestMessage(long messageId) {
        super.setMessageId(messageId);
    }

    public AddFriendRequestMessage(long messageId, String userId, String friendId) {
        super.setMessageId(messageId);
        this.userId = userId;
        this.friendId = friendId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFriendId() {
        return friendId;
    }

    public void setFriendId(String friendId) {
        this.friendId = friendId;
    }

    @Override
    public Byte getConstant() {
        return MessageTypeConstants.AddFriendRequestMessage;
    }

}
