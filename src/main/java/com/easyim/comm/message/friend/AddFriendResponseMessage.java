package com.easyim.comm.message.friend;

import com.easyim.comm.message.Message;
import com.easyim.comm.message.MessageTypeConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 添加好友响应消息
 *
 * @author 单程车票
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class AddFriendResponseMessage extends Message {

    /**
     * 添加好友ID
     */
    private String friendId;

    /**
     * 添加好友昵称
     */
    private String friendNickname;

    /**
     * 添加好友头像
     */
    private String friendAvatar;

    @Override
    public Byte getConstant() {
        return MessageTypeConstants.AddFriendResponseMessage;
    }

}