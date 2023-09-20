package com.easyim.comm.message.login;

import com.easyim.comm.message.Message;
import com.easyim.comm.message.MessageTypeConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 登录请求消息类
 *
 * @author 单程车票
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestMessage extends Message {

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 用户密码
     */
    private String userPassword;

    @Override
    public Byte getConstant() {
        return MessageTypeConstants.LoginRequestMessage;
    }

}