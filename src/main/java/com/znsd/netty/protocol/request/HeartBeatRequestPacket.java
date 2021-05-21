package com.znsd.netty.protocol.request;

import com.znsd.netty.protocol.Packet;
import com.znsd.netty.protocol.command.Command;

/**
 * @ClassName HeartBeatRequestPacket
 * @Author HETAO
 * @Date 2021/5/20 16:17
 */
public class  HeartBeatRequestPacket extends Packet {
    @Override
    public Byte getCommand() {
        return Command.HEART_BEAT_REQUEST;
    }
}
