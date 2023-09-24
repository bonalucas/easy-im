package com.easyim.server;

import com.easyim.comm.protocol.MessageCodec;
import com.easyim.comm.protocol.ProcotolFrameDecoder;
import com.easyim.server.handler.*;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Netty 服务器调用链
 *
 * @author 单程车票
 */
@Component
@ChannelHandler.Sharable
public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Autowired
    private MessageCodec messageCodec;

    @Autowired
    private LoginHandler loginHandler;

    @Autowired
    private AuthHandler authHandler;

    @Autowired
    private RegisterHandler registerHandler;

    @Autowired
    private SearchFriendHandler searchFriendHandler;

    @Autowired
    private AddFriendHandler addFriendHandler;

    @Autowired
    private DialogNoticeHandler dialogNoticeHandler;

    @Autowired
    private DeleteDialogHandler deleteDialogHandler;

    @Autowired
    private ChatHandler chatHandler;

    @Autowired
    private FileUploadHandler fileUploadHandler;

    @Autowired
    private ReconnectHandler reconnectHandler;

    @Autowired
    private HeartBeatHandler heartBeatHandler;

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        // 添加空闲检测
        socketChannel.pipeline().addLast(new ServerIdleStateHandler());
        // 添加长度字段解码器
        socketChannel.pipeline().addLast(new ProcotolFrameDecoder());
        // 添加自定义编解码器
        socketChannel.pipeline().addLast(messageCodec);
        // 添加心跳包处理器
        socketChannel.pipeline().addLast(heartBeatHandler);
        // 添加自定义业务处理器
        socketChannel.pipeline().addLast(loginHandler);
        socketChannel.pipeline().addLast(authHandler);
        socketChannel.pipeline().addLast(registerHandler);
        socketChannel.pipeline().addLast(searchFriendHandler);
        socketChannel.pipeline().addLast(addFriendHandler);
        socketChannel.pipeline().addLast(dialogNoticeHandler);
        socketChannel.pipeline().addLast(deleteDialogHandler);
        socketChannel.pipeline().addLast(chatHandler);
        socketChannel.pipeline().addLast(fileUploadHandler);
        socketChannel.pipeline().addLast(reconnectHandler);
    }
}
