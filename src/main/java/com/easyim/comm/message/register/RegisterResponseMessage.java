package com.easyim.comm.message.register;

import com.easyim.comm.message.Message;
import com.easyim.comm.message.MessageTypeConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 注册响应信息
 *
 * @author 单程车票
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class RegisterResponseMessage extends Message {

    /**
     * 响应信息
     */
    private String responseMsg;

    @Override
    public Byte getConstant() {
        return MessageTypeConstants.RegisterResponseMessage;
    }

}
