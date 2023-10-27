package com.easyim.comm.server.handler;

import com.easyim.comm.server.common.ServerChannelUtil;
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
        if (!ServerChannelUtil.hasAuth(ctx.channel().id().toString())) {
            ctx.channel().close();
        } else {
            // 校验成功后删除当前处理器，避免后续重复校验浪费性能
            ctx.pipeline().remove(AuthHandler.class.getSimpleName());
        }
    }

}
