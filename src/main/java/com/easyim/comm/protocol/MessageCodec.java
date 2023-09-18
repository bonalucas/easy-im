package com.easyim.comm.protocol;

import com.easyim.comm.message.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 编解码器
 *
 * @author 单程车票
 */
@Slf4j
@Component
public class MessageCodec extends MessageToMessageCodec<ByteBuf, Message> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, List<Object> out) throws Exception {
        // 构造编码后的数据
        ByteBuf buffer = ctx.alloc().buffer();
        // 写入消息类型
        buffer.writeByte(msg.getConstant());
        // 写入序列化消息的长度与内容
        byte[] msg_bytes = ProtobufSerializationUtil.serialize(msg);
        buffer.writeByte(msg_bytes.length);
        buffer.writeBytes(msg_bytes);
        // 输出
        out.add(buffer);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        // 获取解码后的消息类型
        byte msg_type = in.readByte();
        // 读取解码后的消息长度
        int msg_len = in.readInt();
        // 构造数据字节数组
        byte[] msg_bytes = new byte[msg_len];
        // 获取解码后的消息数据
        in.readBytes(msg_bytes);
        // 通过反序列化获取消息数据
        Message msg = ProtobufSerializationUtil.deserialize(msg_bytes, Message.get(msg_type));
        // 输出
        out.add(msg);
    }

}
