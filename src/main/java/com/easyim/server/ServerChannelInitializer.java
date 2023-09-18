package com.easyim.server;

import com.easyim.comm.protocol.MessageCodec;
import com.easyim.comm.protocol.ProcotolFrameDecoder;
import com.easyim.server.handler.*;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
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
    private SearchFriendHandler searchFriendHandler;

    @Autowired
    private AddFriendHandler addFriendHandler;

    @Autowired
    private DialogNoticeHandler dialogNoticeHandler;

    @Autowired
    private DeleteDialogHandler deleteDialogHandler;

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        socketChannel.pipeline().addLast(procotolFrameDecoder);
        socketChannel.pipeline().addLast(messageCodec);
        socketChannel.pipeline().addLast(loginHandler);
        socketChannel.pipeline().addLast(searchFriendHandler);
        socketChannel.pipeline().addLast(addFriendHandler);
        socketChannel.pipeline().addLast(dialogNoticeHandler);
        socketChannel.pipeline().addLast(deleteDialogHandler);
    }
}
