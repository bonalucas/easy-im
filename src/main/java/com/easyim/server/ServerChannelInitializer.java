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

    @Autowired
    private TestMessageHandler testMessageHandler;

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        // 添加空闲检测
        socketChannel.pipeline().addLast(ServerChannelInitializer.class.getSimpleName(), new ServerIdleStateHandler());
        // 添加长度字段解码器
        socketChannel.pipeline().addLast(ProcotolFrameDecoder.class.getSimpleName(), new ProcotolFrameDecoder());
        // 添加自定义编解码器
        socketChannel.pipeline().addLast(MessageCodec.class.getSimpleName(), messageCodec);
        // 添加心跳包处理器
        socketChannel.pipeline().addLast(HeartBeatHandler.class.getSimpleName(), heartBeatHandler);
        // 添加自定义业务处理器
        socketChannel.pipeline().addLast(LoginHandler.class.getSimpleName(), loginHandler);
        socketChannel.pipeline().addLast(AuthHandler.class.getSimpleName(), authHandler);
        socketChannel.pipeline().addLast(ReconnectHandler.class.getSimpleName(), registerHandler);
        socketChannel.pipeline().addLast(SearchFriendHandler.class.getSimpleName(), searchFriendHandler);
        socketChannel.pipeline().addLast(AddFriendHandler.class.getSimpleName(), addFriendHandler);
        socketChannel.pipeline().addLast(DialogNoticeHandler.class.getSimpleName(), dialogNoticeHandler);
        socketChannel.pipeline().addLast(DeleteDialogHandler.class.getSimpleName(), deleteDialogHandler);
        socketChannel.pipeline().addLast(ChatHandler.class.getSimpleName(), chatHandler);
        socketChannel.pipeline().addLast(FileUploadHandler.class.getSimpleName(), fileUploadHandler);
        socketChannel.pipeline().addLast(ReconnectHandler.class.getSimpleName(), reconnectHandler);
        socketChannel.pipeline().addLast(TestMessageHandler.class.getSimpleName(), testMessageHandler);
    }
}
