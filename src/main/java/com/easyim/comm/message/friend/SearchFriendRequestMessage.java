package com.easyim.comm.message.friend;

import com.easyim.comm.message.Message;
import com.easyim.comm.message.MessageTypeConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 查找好友请求消息
 *
 * @author 单程车票
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class SearchFriendRequestMessage extends Message {

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 搜索字段
     */
    private String searchKey;

    public SearchFriendRequestMessage(String messageId, Boolean status) {
        super.setMessageId(messageId);
        super.setStatus(status);
    }

    public SearchFriendRequestMessage(String messageId, Boolean status, String userId, String searchKey) {
        super.setMessageId(messageId);
        super.setStatus(status);
        this.userId = userId;
        this.searchKey = searchKey;
    }

    @Override
    public Byte getConstant() {
        return MessageTypeConstants.SearchFriendRequestMessage;
    }
}
