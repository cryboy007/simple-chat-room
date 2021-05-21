package com.znsd.netty.server.handler;

import com.znsd.netty.util.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @ClassName AuthHandler
 * @Author HETAO
 * @Date 2021/5/20 16:22
 */
public class AuthHandler extends ChannelInboundHandlerAdapter {
    /**
     * 热插拔实现客户端身份校验
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (!SessionUtil.hasLogin(ctx.channel())) {
            ctx.channel().close();
        }else {
            ctx.pipeline().remove(this);
            super.channelRead(ctx, msg);
        }
    }
}
