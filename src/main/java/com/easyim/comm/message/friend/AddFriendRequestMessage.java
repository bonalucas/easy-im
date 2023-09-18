package com.easyim.comm.message.friend;

import com.easyim.comm.message.Message;
import com.easyim.comm.message.MessageTypeConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 添加好友请求消息
 *
 * @author 单程车票
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class AddFriendRequestMessage extends Message {

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 添加好友ID
     */
    private String friendId;

    @Override
    public Byte getConstant() {
        return MessageTypeConstants.AddFriendRequestMessage;
    }
}
