package com.znsd.netty.protocol.response;

import com.znsd.netty.protocol.Packet;
import com.znsd.netty.protocol.command.Command;
import lombok.Data;

import java.util.List;

/**
 * @ClassName CreateGroupResponsePacket
 * @Author HETAO
 * @Date 2021/5/20 10:22
 */
@Data
public class CreateGroupResponsePacket extends Packet {
    private boolean success;

    private String groupId;

    private String[] memberNameList;

    @Override
    public Byte getCommand() {
        return Command.CREATE_GROUP_RESPONSE;
    }
}
