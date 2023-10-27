package com.easyim.comm.server;

import com.easyim.comm.protocol.MessageCodec;
import com.easyim.comm.protocol.ProtocolFrameDecoder;
import com.easyim.comm.server.handler.*;
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
    private MonitorHandler monitorHandler;

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
        socketChannel.pipeline().addLast(ServerIdleStateHandler.class.getSimpleName(), new ServerIdleStateHandler());
        // 添加解决TCP粘包半包问题解码器
        socketChannel.pipeline().addLast(ProtocolFrameDecoder.class.getSimpleName(), new ProtocolFrameDecoder());
        // 添加私有协议编解码器
        socketChannel.pipeline().addLast("Easy-IM-MessageCodec", MessageCodec.getInstance());
        // 添加心跳包处理器
        socketChannel.pipeline().addLast(HeartBeatHandler.class.getSimpleName(), heartBeatHandler);
        // 添加自定义业务处理器
//        socketChannel.pipeline().addLast(LoginHandler.class.getSimpleName(), loginHandler);
//        socketChannel.pipeline().addLast(AuthHandler.class.getSimpleName(), authHandler);
//        socketChannel.pipeline().addLast(RegisterHandler.class.getSimpleName(), registerHandler);
//        socketChannel.pipeline().addLast(SearchFriendHandler.class.getSimpleName(), searchFriendHandler);
//        socketChannel.pipeline().addLast(AddFriendHandler.class.getSimpleName(), addFriendHandler);
//        socketChannel.pipeline().addLast(DialogNoticeHandler.class.getSimpleName(), dialogNoticeHandler);
//        socketChannel.pipeline().addLast(DeleteDialogHandler.class.getSimpleName(), deleteDialogHandler);
//        socketChannel.pipeline().addLast(ChatHandler.class.getSimpleName(), chatHandler);
//        socketChannel.pipeline().addLast(FileUploadHandler.class.getSimpleName(), fileUploadHandler);
//        socketChannel.pipeline().addLast(ReconnectHandler.class.getSimpleName(), reconnectHandler);
        socketChannel.pipeline().addLast(TestMessageHandler.class.getSimpleName(), testMessageHandler);
        // 添加监控处理器
        socketChannel.pipeline().addLast(MonitorHandler.class.getSimpleName(), monitorHandler);
    }
}
