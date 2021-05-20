package com.znsd.netty.server.handler;

import com.znsd.netty.protocol.request.QueryGroupRequestPacket;
import com.znsd.netty.protocol.response.ExceptionResponsePacket;
import com.znsd.netty.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * @ClassName QueryGroupRequestHandler
 * @Author HETAO
 * @Date 2021/5/20 11:26
 */
public class QueryGroupRequestHandler extends SimpleChannelInboundHandler<QueryGroupRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, QueryGroupRequestPacket msg) throws Exception {
        String groupId = msg.getGroupId();
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);
        Set<String> userNameList = new HashSet<>();
        if (channelGroup == null) {
            ExceptionResponsePacket responsePacket = new ExceptionResponsePacket();
            responsePacket.setMessage("没有找到这个群组");
            ctx.channel().writeAndFlush(responsePacket);
            return;
        }
        for (Channel channel : channelGroup) {
            String userName = SessionUtil.getSession(channel).getUserName();
            userNameList.add(userName);
        }
        QueryGroupRequestPacket responsePacket = new QueryGroupRequestPacket();
        responsePacket.setGroupId(groupId);
        responsePacket.setMemberNameList(new ArrayList<>(userNameList));
        ctx.channel().writeAndFlush(responsePacket);
    }
}
