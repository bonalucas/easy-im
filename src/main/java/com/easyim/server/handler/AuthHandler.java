package com.easyim.server.handler;

import com.easyim.server.util.SocketChannelUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 登录认证处理器
 *
 * @author 单程车票
 */
@Slf4j
@Component
@ChannelHandler.Sharable
public class AuthHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 校验登录认证
        if (!SocketChannelUtil.hasAuth(ctx.channel().id().toString())) {
            ctx.channel().close();
        } else {
            // 校验成功后删除当前处理器，避免后续重复校验浪费性能
            ctx.pipeline().remove(this);
            super.channelRead(ctx, msg);
        }
    }

}
