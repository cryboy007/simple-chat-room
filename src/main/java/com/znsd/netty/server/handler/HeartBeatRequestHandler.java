package com.znsd.netty.server.handler;

import com.znsd.netty.protocol.request.HeartBeatRequestPacket;
import com.znsd.netty.protocol.response.HeartBeatResponsePacket;
import com.znsd.netty.util.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.concurrent.EventExecutorGroup;

/**
 * @ClassName HeartBeatRequestHandler
 * @Author HETAO
 * @Date 2021/5/20 17:23
 */
public class HeartBeatRequestHandler extends SimpleChannelInboundHandler<HeartBeatRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HeartBeatRequestPacket msg) throws Exception {
        System.out.println("收到" + SessionUtil.getSession(ctx.channel()).getUserName()+"用户发来的心跳");
        ctx.channel().writeAndFlush(new HeartBeatResponsePacket());
    }
}
