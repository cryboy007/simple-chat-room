package com.znsd.netty.client.handler;

import com.znsd.netty.protocol.response.LogOutResponsePacket;
import com.znsd.netty.util.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @ClassName LogOutResponseHandler
 * @Author HETAO
 * @Date 2021/5/19 17:57
 */
public class LogOutResponseHandler extends SimpleChannelInboundHandler<LogOutResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LogOutResponsePacket msg) throws Exception {
        SessionUtil.unBindSession(ctx.channel());
    }
}
