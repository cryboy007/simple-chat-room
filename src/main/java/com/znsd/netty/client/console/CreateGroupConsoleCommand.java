package com.znsd.netty.client.console;

import com.znsd.netty.protocol.request.CreateGroupRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @ClassName CreateGroupConsoleCommand
 * @Author HETAO
 * @Date 2021/5/20 10:02
 */
public class CreateGroupConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.println("请输入要拉去的成员userId用逗号分隔");
        String memberIds = scanner.next();
        String[] memberList = memberIds.split(",");
        CreateGroupRequestPacket groupRequestPacket = new CreateGroupRequestPacket();
        groupRequestPacket.setMemberList(memberList);
        channel.writeAndFlush(groupRequestPacket);
    }
}
