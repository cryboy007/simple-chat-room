package com.znsd.netty.client.console;

import com.znsd.netty.protocol.request.QueryGroupRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @ClassName QueryMemberListConsoleCommand
 * @Author HETAO
 * @Date 2021/5/20 11:21
 */
public class QueryMemberListConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.println("请输入你要查看的群组id");
        String groupId = scanner.next();
        QueryGroupRequestPacket queryGroupRequestPacket = new QueryGroupRequestPacket();
        queryGroupRequestPacket.setGroupId(groupId);

        channel.writeAndFlush(queryGroupRequestPacket);
    }
}
