package com.easyim.server;

import com.easyim.comm.protocol.MessageCodec;
import com.easyim.comm.protocol.ProcotolFrameDecoder;
import com.easyim.server.handler.*;
import com.easyim.server.util.SocketChannelUtil;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 *
 * @author 单程车票
 */
@Component
public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Autowired
    private ProcotolFrameDecoder procotolFrameDecoder;

    @Autowired
    private MessageCodec messageCodec;

    @Autowired
    private LoginHandler loginHandler;

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

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        // 添加长度字段解码器
        socketChannel.pipeline().addLast(procotolFrameDecoder);
        // 添加自定义编解码器
        socketChannel.pipeline().addLast(messageCodec);
        // 添加心跳检测（10s内未读到数据触发 READER_IDLE 时间断开连接）
        socketChannel.pipeline().addLast(new IdleStateHandler(10, 0, 0));
        socketChannel.pipeline().addLast(new ChannelDuplexHandler() {
            @Override
            public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
                IdleStateEvent event = (IdleStateEvent) evt;
                if (event.state() == IdleState.READER_IDLE) {
                    ctx.channel().close();
                }
            }
        });
        // 添加自定义业务处理器
        socketChannel.pipeline().addLast(loginHandler);
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
