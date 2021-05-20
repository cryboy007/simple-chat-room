package com.znsd.netty.protocol.response;

import com.znsd.netty.protocol.Packet;
import com.znsd.netty.protocol.command.Command;
import lombok.Data;

/**
 * @ClassName MessageResponsePacket
 * @Author HETAO
 * @Date 2021/5/19 15:22
 */
@Data
public class MessageResponsePacket extends Packet {
    private String fromUserId;

    private String fromUserName;

    private String message;
    @Override
    public Byte getCommand() {
        return Command.MESSAGE_RESPONSE;
    }
}
