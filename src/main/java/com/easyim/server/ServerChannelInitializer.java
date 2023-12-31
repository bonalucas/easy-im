package com.easyim.server;

import com.easyim.comm.protocol.MessageCodec;
import com.easyim.comm.protocol.ProtocolFrameDecoder;
import com.easyim.server.handler.EasyIMIdleStateHandler;
import com.easyim.server.handler.ExceptionHandler;
import com.easyim.server.handler.HandShakeHandler;
import com.easyim.server.handler.HeartBeatHandler;
import com.easyim.server.handler.biz.*;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import org.springframework.stereotype.Component;

/**
 * Netty 服务器调用链
 *
 * @author 单程车票
 */
@Component
@ChannelHandler.Sharable
public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        // 添加空闲检测
        socketChannel.pipeline().addLast("Easy-IM-IdleStateHandler", new EasyIMIdleStateHandler());
        // 添加解决TCP粘包半包问题解码器
        socketChannel.pipeline().addLast("Easy-IM-ProtocolFrameDecoder", new ProtocolFrameDecoder());
        // 添加私有协议编解码器
        socketChannel.pipeline().addLast("Easy-IM-MessageCodec", MessageCodec.getInstance());
        // 添加握手处理器
        socketChannel.pipeline().addLast("HandShake-Handler", HandShakeHandler.getInstance());
        // 添加心跳包处理器
        socketChannel.pipeline().addLast("HeartBeat-Handler", HeartBeatHandler.getInstance());
        // 添加自定义业务处理器
        socketChannel.pipeline().addLast("CreateMeeting-Handler", CreateMeetingHandler.getInstance());
        socketChannel.pipeline().addLast("JoinMeeting-Handler", JoinMeetingHandler.getInstance());
        socketChannel.pipeline().addLast("Chat-Handler", ChatHandler.getInstance());
        socketChannel.pipeline().addLast("File-Handler", FileHandler.getInstance());
        socketChannel.pipeline().addLast("ShareScreen-Handler", ShareScreenHandler.getInstance());
        socketChannel.pipeline().addLast("LeaveMeeting-Handler", LeaveMeetingHandler.getInstance());
        socketChannel.pipeline().addLast("ExitScreen-Handler", ExitScreenHandler.getInstance());
        // 添加异常处理器
        socketChannel.pipeline().addLast("Exception-Handler", ExceptionHandler.getInstance());
    }

}
