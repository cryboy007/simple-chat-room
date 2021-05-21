package com.znsd.netty.client.handler;

import com.znsd.netty.protocol.request.HeartBeatRequestPacket;
import com.znsd.netty.protocol.response.HeartBeatResponsePacket;
import com.znsd.netty.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;

import java.net.URI;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName HeartBeatTimerHandler
 * @Author HETAO
 * @Date 2021/5/20 14:53
 */
public class HeartBeatTimerHandler extends ChannelInboundHandlerAdapter {

    private static final int HEARTBEAT_INTERVAL = 5;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        sendHeartbeatRegularly(ctx);
        super.channelActive(ctx);
    }

    /**
     * 每5秒发送一次心跳
     * @param ctx
     */
    private void sendHeartbeatRegularly(ChannelHandlerContext ctx) {
        ctx.executor().schedule(() -> {
            if (ctx.channel().isActive()) {
                //System.out.println(SessionUtil.getSession(ctx.channel()).getUserName() + "发送了心跳");
                //ctx.channel().writeAndFlush(new HeartBeatRequestPacket());
                ctx.writeAndFlush(new HeartBeatRequestPacket());
                sendHeartbeatRegularly(ctx);
            }
        }, HEARTBEAT_INTERVAL, TimeUnit.SECONDS);
    }
}
