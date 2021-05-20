package com.znsd.netty.client.handler;

import com.znsd.netty.protocol.request.QueryGroupRequestPacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Arrays;
import java.util.List;

/**
 * @ClassName QueryGroupMemberHandler
 * @Author HETAO
 * @Date 2021/5/20 11:30
 */
public class QueryGroupMemberHandler extends SimpleChannelInboundHandler<QueryGroupRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, QueryGroupRequestPacket msg) throws Exception {
        final List<String> memberNameList = msg.getMemberNameList();
        System.out.println("群组:"+msg.getGroupId()+"里的成员有:"+ Arrays.toString(memberNameList.toArray()));
    }
}
