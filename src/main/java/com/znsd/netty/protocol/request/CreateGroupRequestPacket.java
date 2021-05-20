package com.znsd.netty.protocol.request;

import com.znsd.netty.protocol.Packet;
import com.znsd.netty.protocol.command.Command;
import lombok.Data;

/**
 * @ClassName GroupRequestPacket
 * @Author HETAO
 * @Date 2021/5/20 10:05
 */
@Data
public class CreateGroupRequestPacket extends Packet {
    private String[] memberList;

    @Override
    public Byte getCommand() {
        return Command.CREATE_GROUP_REQUEST;
    }
}
