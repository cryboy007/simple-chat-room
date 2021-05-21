package com.znsd.netty.server;

import com.znsd.netty.codec.PacketCodecHandler;
import com.znsd.netty.codec.Unpacker;
import com.znsd.netty.handler.IMIdleStateHandler;
import com.znsd.netty.server.handler.*;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @ClassName NettyServer
 * @Author HETAO
 * @Date 2021/5/18 16:55
 */
public class NettyServer {
    private static int PORT = 8080;


    public static void main(String[] args) {
        NioEventLoopGroup boosGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        final ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap
                .group(boosGroup,workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG,1024)
                .childOption(ChannelOption.SO_KEEPALIVE,true)
                .childOption(ChannelOption.TCP_NODELAY,true)
                .childOption(ChannelOption.SO_TIMEOUT,100)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new IMIdleStateHandler());
                        ch.pipeline().addLast(new Unpacker());
                        ch.pipeline().addLast(new PacketCodecHandler());
                        ch.pipeline().addLast(new LoginRequestHandler());
                        //因为客户端会定期发送心跳包，我们给他回个就行了
                        ch.pipeline().addLast(new HeartBeatRequestHandler());
                        ch.pipeline().addLast(new AuthHandler());
                        ch.pipeline().addLast(IMHandler.INSTANCE);
                    }
                });
                bind(serverBootstrap,PORT);
    }

    private static void bind(ServerBootstrap serverBootstrap,int port) {
        serverBootstrap.bind(port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("服务器启动成功,端口为:"+port);
            }else {
                System.out.println("服务器启动失败,重新选择端口"+ (port + 1));
                bind(serverBootstrap, port + 1);
            }
        });
    }
}
