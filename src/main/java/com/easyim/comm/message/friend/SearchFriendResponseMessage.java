package com.easyim.comm.message.friend;

import com.easyim.comm.message.Message;
import com.easyim.comm.message.MessageTypeConstants;
import com.easyim.comm.message.friend.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 查找好友响应消息
 *
 * @author 单程车票
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class SearchFriendResponseMessage extends Message {

    /**
     * 查找好友列表
     */
    private List<UserDto> list;

    @Override
    public Byte getConstant() {
        return MessageTypeConstants.SearchFriendResponseMessage;
    }

}
