package com.znsd.netty.client.console;

import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @ClassName ConsoleCommand
 * @Author HETAO
 * @Date 2021/5/19 16:03
 */
public interface ConsoleCommand  {
    void exec(Scanner scanner, Channel channel);
}
