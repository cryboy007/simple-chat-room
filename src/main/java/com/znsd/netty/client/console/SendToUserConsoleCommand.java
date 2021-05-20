package com.znsd.netty.client.console;

import com.znsd.netty.protocol.request.MessageRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @ClassName SendToUserConsoleCommand
 * @Author HETAO
 * @Date 2021/5/19 16:08
 */
public class SendToUserConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.print("发送消息给某个某个用户：");
        String toUserId = scanner.next();

        String message = scanner.next();
        channel.writeAndFlush(new MessageRequestPacket(toUserId, message));
    }
}
