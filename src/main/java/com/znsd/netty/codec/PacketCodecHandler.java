package com.znsd.netty.codec;

import com.znsd.netty.protocol.Packet;
import com.znsd.netty.protocol.PacketCodec;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;

import java.util.List;

/**
 * @ClassName PacketCodecHandler
 * @Author HETAO
 * @Date 2021/5/19 10:53
 */
public class PacketCodecHandler extends MessageToMessageCodec<ByteBuf, Packet> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Packet msg, List<Object> out) throws Exception {
        ByteBuf byteBuf = ctx.channel().alloc().ioBuffer();
        PacketCodec.INSTANCE.encode(byteBuf,msg);
        out.add(byteBuf);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        Packet packet = PacketCodec.INSTANCE.decode(msg);
        if (packet != null) {
            out.add(packet);
        }
    }
}
