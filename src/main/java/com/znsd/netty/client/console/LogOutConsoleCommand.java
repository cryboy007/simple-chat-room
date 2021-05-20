package com.znsd.netty.client.console;

import com.znsd.netty.protocol.request.LogOutRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @ClassName LogOutConsoleCommand
 * @Author HETAO
 * @Date 2021/5/19 17:46
 */
public class LogOutConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        LogOutRequestPacket logoutRequestPacket = new LogOutRequestPacket();
        channel.writeAndFlush(logoutRequestPacket);
    }
}
