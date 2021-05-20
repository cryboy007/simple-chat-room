package com.znsd.netty.server.handler;

import com.znsd.netty.attribute.Attributes;
import com.znsd.netty.protocol.request.CreateGroupRequestPacket;
import com.znsd.netty.protocol.response.CreateGroupResponsePacket;
import com.znsd.netty.util.IDUtil;
import com.znsd.netty.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;

import java.util.*;

/**
 * @ClassName CreateGroupRequestHandler
 * @Author HETAO
 * @Date 2021/5/20 10:15
 */
public class CreateGroupRequestHandler extends SimpleChannelInboundHandler<CreateGroupRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupRequestPacket msg) throws Exception {
        final String[] memberList = msg.getMemberList();
        final String groupId = IDUtil.randomId();
        //创建一个channel分组
        ChannelGroup channelGroup = new DefaultChannelGroup(ctx.executor());
        Set<String> userNameList = new HashSet<>();
        //从session获取要加入群组的成员
        for (String member : memberList) {
            Channel channel = SessionUtil.getChannel(member);
            if (channel != null) {
                channelGroup.add(channel);
                userNameList.add(SessionUtil.getSession(channel).getUserName());
            }
        }
        //创群者自己
        channelGroup.add(ctx.channel());
        userNameList.add(ctx.channel().attr(Attributes.SESSION).get().getUserName());

        CreateGroupResponsePacket groupResponsePacket = new CreateGroupResponsePacket();
        groupResponsePacket.setMemberNameList(userNameList.toArray(new String[userNameList.size()]));
        groupResponsePacket.setSuccess(true);
        groupResponsePacket.setGroupId(groupId);
        // 4. 给每个客户端发送拉群通知
        channelGroup.writeAndFlush(groupResponsePacket);
        //方便后续退群
        SessionUtil.bindChannelGroup(groupId,channelGroup);

        System.out.print("群创建成功，id 为[" + groupResponsePacket.getGroupId() + "], ");
        System.out.println("群里面有：" + Arrays.toString(groupResponsePacket.getMemberNameList()));
    }
}
