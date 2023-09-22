package com.easyim.comm.message.reconnect;

import com.easyim.comm.message.Message;
import com.easyim.comm.message.MessageTypeConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 断线重连请求消息
 *
 * @author 单程车票
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class ReconnectRequestMessage extends Message {

    /**
     * 用户唯一ID
     */
    private String userId;

    @Override
    public Byte getConstant() {
        return MessageTypeConstants.ReconnectRequestMessage;
    }
}
