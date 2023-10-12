package com.easyim.comm.message.register;

import com.easyim.comm.message.Message;
import com.easyim.comm.message.MessageTypeConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 注册请求信息
 *
 * @author 单程车票
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class RegisterRequestMessage extends Message {

    /**
     * 账户
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像
     */
    private String avatar;

    public RegisterRequestMessage(String messageId, Boolean status) {
        super.setMessageId(messageId);
        super.setStatus(status);
    }

    public RegisterRequestMessage(String messageId, Boolean status, String username, String password, String nickname, String avatar) {
        super.setMessageId(messageId);
        super.setStatus(status);
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.avatar = avatar;
    }

    @Override
    public Byte getConstant() {
        return MessageTypeConstants.RegisterRequestMessage;
    }

}
