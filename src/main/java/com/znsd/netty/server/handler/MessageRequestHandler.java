package com.znsd.netty.server.handler;

import com.znsd.netty.protocol.request.MessageRequestPacket;
import com.znsd.netty.protocol.response.MessageResponsePacket;
import com.znsd.netty.session.Session;
import com.znsd.netty.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @ClassName MessageRequestHandler
 * @Author HETAO
 * @Date 2021/5/19 15:20
 */
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {
    /**
     * 这里，我们首先通过channel获取到发送方的userId和userName
     * 然后通过客户端发送过来的接收方userId获取到channel
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket msg) throws Exception {
        // 1.拿到消息发送方的会话信息
        Session session = SessionUtil.getSession(ctx.channel());

        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
        messageResponsePacket.setFromUserId(session.getUserId());
        messageResponsePacket.setFromUserName(session.getUserName());
        messageResponsePacket.setMessage(msg.getMessage());

        // 3.拿到消息接收方的 channel
        Channel toUserChannel = SessionUtil.getChannel(msg.getToUserId());

        if (toUserChannel != null && SessionUtil.hasLogin(toUserChannel)) {
            toUserChannel.writeAndFlush(messageResponsePacket).addListener(future -> {
                if (future.isDone()) {

                }
            });
        }else {
            System.err.println("[" + session.getUserId() + "] 不在线，发送失败!");
        }
    }
}
