package com.easyim.comm.message.test;

import com.easyim.comm.message.Message;
import com.easyim.comm.message.MessageTypeConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class TestRequestMessage extends Message {

    private String content;

    @Override
    public Byte getConstant() {
        return MessageTypeConstants.TestRequestMessage;
    }
}
