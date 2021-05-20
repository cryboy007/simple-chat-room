package com.znsd.netty.client.handler;

import com.znsd.netty.protocol.response.CreateGroupResponsePacket;
import com.znsd.netty.session.Session;
import com.znsd.netty.util.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * @ClassName CreateGroupResponseHandler
 * @Author HETAO
 * @Date 2021/5/20 10:34
 */
public class CreateGroupResponseHandler extends SimpleChannelInboundHandler<CreateGroupResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupResponsePacket msg) throws Exception {
        String[] memberNameList = msg.getMemberNameList();
        String groupId = msg.getGroupId();
        Session session = SessionUtil.getSession(ctx.channel());

        List<String> memberList = new ArrayList<>(Arrays.asList(memberNameList));
        memberList.remove(session.getUserName());
        System.out.print("群创建成功, id为["+groupId+"]");
        System.out.println("群里面有:"+memberList.toString());
    }
}
