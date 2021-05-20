package com.znsd.netty.server.handler;

import com.znsd.netty.protocol.Packet;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.HashMap;
import java.util.Map;

import static com.znsd.netty.protocol.command.Command.*;

/**
 * @ClassName IMHandler
 * @Author HETAO
 * @Date 2021/5/20 14:21
 */
@ChannelHandler.Sharable
public class IMHandler extends SimpleChannelInboundHandler<Packet> {
    /**
     * 缩短事件传播路径
     */
    public static final IMHandler INSTANCE = new IMHandler();

    private Map<Byte, SimpleChannelInboundHandler<? extends Packet>> handlerMap;

    private IMHandler() {
        handlerMap = new HashMap<>();
        //这里可以优化成单例模式，我这里就不做了
        handlerMap.put(MESSAGE_REQUEST,new MessageRequestHandler());
        handlerMap.put(LOGIN_REQUEST,new LoginRequestHandler());
        handlerMap.put(MESSAGE_REQUEST,new MessageRequestHandler());
        handlerMap.put(LOGOUT_REQUEST,new LogOutRequestHandler());
        handlerMap.put(CREATE_GROUP_REQUEST,new CreateGroupRequestHandler());
        handlerMap.put(QUIT_GROUP_REQUEST,new QuitGroupRequestHandler());
        handlerMap.put(QUERY_GROUP_MEMBER,new QueryGroupRequestHandler());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet msg) throws Exception {
        handlerMap.get(msg.getCommand()).channelRead(ctx,msg);
    }
}
