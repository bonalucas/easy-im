package com.easyim.test;

import com.easyim.comm.message.Message;
import com.easyim.comm.message.test.TestRequestMessage;
import com.easyim.comm.protocol.MessageCodec;
import com.easyim.comm.protocol.ProtobufSerializer;
import com.easyim.comm.protocol.ProtocolFrameDecoder;
import com.easyim.comm.server.common.SnowflakeIDGenerator;
import com.easyim.comm.server.handler.ExceptionHandler;
import com.easyim.comm.server.handler.TestMessageHandler;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 管道处理器测试类
 *
 * @author 单程车票
 */
@SpringBootTest
public class ChannelPipelineTest {

    @Test
    public void testPipeline() {
        EmbeddedChannel channel = new EmbeddedChannel(
                        new ProtocolFrameDecoder(),
                        new MessageCodec(),
                        new TestMessageHandler(),
                        new ExceptionHandler());
        channel.writeInbound(encode(new TestRequestMessage(SnowflakeIDGenerator.generateID(), "hello")));
        channel.finish();
    }

    private ByteBuf encode(Message message) {
        ByteBuf buffer = Unpooled.buffer();
        buffer.writeInt(0xEAEA2023);
        buffer.writeByte(message.getConstant());
        buffer.writeByte(0);
        byte[] msg_bytes = ProtobufSerializer.serialize(message);
        buffer.writeInt(msg_bytes.length);
        buffer.writeBytes(msg_bytes);
        return buffer;
    }

}
