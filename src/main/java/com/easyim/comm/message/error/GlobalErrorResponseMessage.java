package com.easyim.comm.message.error;

import com.easyim.comm.message.Message;
import com.easyim.comm.message.MessageTypeConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 全局异常响应消息
 *
 * @author 单程车票
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class GlobalErrorResponseMessage extends Message {

    private String error;

    @Override
    public Byte getConstant() {
        return MessageTypeConstants.GlobalErrorResponseMessage;
    }

}
