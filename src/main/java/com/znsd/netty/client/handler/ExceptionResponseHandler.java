package com.znsd.netty.client.handler;

import com.znsd.netty.protocol.response.ExceptionResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @ClassName ExceptionResponseHandler
 * @Author HETAO
 * @Date 2021/5/20 14:34
 */
public class ExceptionResponseHandler extends SimpleChannelInboundHandler<ExceptionResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ExceptionResponsePacket msg) throws Exception {
        System.out.println("错误信息: ->" + msg.getMessage());
    }
}
