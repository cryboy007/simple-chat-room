package com.znsd.netty.client;

import com.znsd.netty.client.console.ConsoleCommandManager;
import com.znsd.netty.client.console.LoginConsoleCommand;
import com.znsd.netty.client.console.QuitGroupConsoleCommand;
import com.znsd.netty.client.handler.*;
import com.znsd.netty.codec.PacketCodecHandler;
import com.znsd.netty.codec.Unpacker;
import com.znsd.netty.handler.IMIdleStateHandler;
import com.znsd.netty.protocol.request.LoginRequestPacket;
import com.znsd.netty.util.SessionUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.BootstrapConfig;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;

import java.net.URL;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName NettyClient
 * @Author HETAO
 * @Date 2021/5/18 17:06
 */
public class NettyClient {
    private static String HOST = "localhost";
    private static int PORT = 8081;
    private static int MAX_RETRY = 5;

    public static void main(String[] args) {
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap()
                .group(workerGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS,5000)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new IMIdleStateHandler());
                        ch.pipeline().addLast(new Unpacker());
                        ch.pipeline().addLast(new PacketCodecHandler());
                        /** 这里其实也可以像Server端一样用策略模式*/
                        ch.pipeline().addLast(new LoginResponseHandler());
                        ch.pipeline().addLast(new MessageResponseHandler());
                        ch.pipeline().addLast(new LogOutResponseHandler());
                        ch.pipeline().addLast(new CreateGroupResponseHandler());
                        ch.pipeline().addLast(new QuitGroupResponseHandler());
                        ch.pipeline().addLast(new QueryGroupMemberHandler());
                        ch.pipeline().addLast(new ExceptionResponseHandler());
                        // 心跳定时器
                        ch.pipeline().addLast(new HeartBeatTimerHandler());
                    }
                });
        connect(bootstrap,HOST,PORT,MAX_RETRY);
    }

    private static void connect(Bootstrap bootstrap, String host, int port,int retry) {
        bootstrap.connect(host,port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println(new Date() + ": 连接成功，启动控制台线程……");
                Channel channel = ((ChannelFuture) future).channel();
                //发消息
                startConsoleThread(channel);
            }else if (retry == 0) {
                System.err.println("重试次数已用完,放弃连接");
                System.exit(0);
            }else {
                //第几次重连
                int order = (MAX_RETRY - retry) + 1;
                int delay = 1 << order;
                System.err.println(new Date() + ": 连接失败，第" + order + "次重连……");
                //定时重连
                bootstrap.config().group().schedule(
                        () -> connect(bootstrap,host,port,retry - 1),delay, TimeUnit.SECONDS);
            }
        });
    }
    private static void startConsoleThread(Channel channel) {
        ConsoleCommandManager consoleCommandManager = new ConsoleCommandManager();
        LoginConsoleCommand loginConsoleCommand = new LoginConsoleCommand();
        Scanner scanner = new Scanner(System.in);

        new Thread(() -> {
            while (!Thread.interrupted()) {
                if (!SessionUtil.hasLogin(channel)) {
                    loginConsoleCommand.exec(scanner, channel);
                } else {
                    consoleCommandManager.exec(scanner, channel);
                }
            }
        }).start();
    }
}
