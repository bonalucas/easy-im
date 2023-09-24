package com.easyim.comm.message.login;

import com.easyim.comm.message.Message;
import com.easyim.comm.message.MessageTypeConstants;
import com.easyim.comm.message.login.dto.DialogDto;
import com.easyim.comm.message.login.dto.FriendDto;
import com.easyim.comm.message.login.dto.GroupDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 登录响应消息类
 *
 * @author 单程车票
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseMessage extends Message {

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 用户头像
     */
    private String userAvatar;

    /**
     * 用户昵称
     */
    private String userNickname;

    /**
     * 对话列表
     */
    private List<DialogDto> dialogList;

    /**
     * 群组列表
     */
    private List<GroupDto> groupList;

    /**
     * 好友列表
     */
    private List<FriendDto> friendList;

    @Override
    public Byte getConstant() {
        return MessageTypeConstants.LoginResponseMessage;
    }

}




