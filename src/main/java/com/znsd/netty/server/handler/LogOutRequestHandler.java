package com.znsd.netty.server.handler;

import com.znsd.netty.protocol.request.LogOutRequestPacket;
import com.znsd.netty.protocol.response.LogOutResponsePacket;
import com.znsd.netty.util.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @ClassName LogOutRequestHandler
 * @Author HETAO
 * @Date 2021/5/19 17:51
 */
public class LogOutRequestHandler extends SimpleChannelInboundHandler<LogOutRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LogOutRequestPacket logOutRequestPacket) throws Exception {
        SessionUtil.unBindSession(ctx.channel());

        LogOutResponsePacket response = new LogOutResponsePacket();
        response.setSuccess(true);
        ctx.writeAndFlush(response);
    }
}
