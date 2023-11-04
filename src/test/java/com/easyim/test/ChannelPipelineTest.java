package com.easyim.test;

import com.easyim.comm.message.Message;
import com.easyim.comm.protocol.MessageCodec;
import com.easyim.comm.protocol.ProtobufSerializer;
import com.easyim.comm.protocol.ProtocolFrameDecoder;
import com.easyim.server.handler.ExceptionHandler;
import com.easyim.server.handler.biz.CreateMeetingHandler;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.util.AttributeKey;
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
                        new CreateMeetingHandler(),
                        new ExceptionHandler());
        channel.writeInbound();
        channel.finish();
    }

    @Test
    public void attrTest() {
        EmbeddedChannel channel = new EmbeddedChannel(
                new ProtocolFrameDecoder(),
                new MessageCodec(),
                new ExceptionHandler());
        channel.attr(AttributeKey.valueOf("meetingID")).set("123456");
        System.out.println(channel.attr(AttributeKey.valueOf("meetingID")).get());
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
