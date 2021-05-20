package com.znsd.netty.server.handler;

import com.znsd.netty.protocol.request.QuitGroupRequestPacket;
import com.znsd.netty.protocol.response.QuitGroupResponsePacket;
import com.znsd.netty.session.Session;
import com.znsd.netty.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

/**
 * @ClassName QuitGroupRequestHandler
 * @Author HETAO
 * @Date 2021/5/20 11:01
 */
public class QuitGroupRequestHandler extends SimpleChannelInboundHandler<QuitGroupRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, QuitGroupRequestPacket msg) throws Exception {
        String groupId = msg.getGroupId();
        final Channel channel = ctx.channel();
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);
        channelGroup.remove(channel);

        // 2. 构造退群响应发送给客户端
        QuitGroupResponsePacket responsePacket = new QuitGroupResponsePacket();

        responsePacket.setGroupId(msg.getGroupId());
        responsePacket.setSuccess(true);
        //这里是只发给了退群发起人，如果要发送给其他成员用channelGroup
        ctx.writeAndFlush(responsePacket);

        //这里可以判断,如果当前群里没有成员就进行释放
        if (channelGroup.size() == 0 ) {
            SessionUtil.closeChannelGroup(groupId);
            channelGroup.close();
            channelGroup = null;
        }
    }
}
