package com.znsd.netty.server;

import com.znsd.netty.codec.PacketCodecHandler;
import com.znsd.netty.codec.Unpacker;
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
                        ch.pipeline().addLast(new Unpacker());
                        ch.pipeline().addLast(new PacketCodecHandler());
                        ch.pipeline().addLast(IMHandler.INSTANCE);
                    }
                });
        serverBootstrap.bind(8899).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("服务器启动成功");
            }else {
                System.out.println("服务器启动失败");
            }
        });
    }
}
