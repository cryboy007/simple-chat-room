package com.znsd.netty.protocol.request;

import com.znsd.netty.protocol.Packet;
import com.znsd.netty.protocol.command.Command;
import lombok.Data;

import java.util.List;

/**
 * @ClassName QueryGroupRequestPacket
 * @Author HETAO
 * @Date 2021/5/20 11:23
 */
@Data
public class QueryGroupRequestPacket extends Packet {
    private String groupId;

    private List<String> memberNameList;

    @Override
    public Byte getCommand() {
        return Command.QUERY_GROUP_MEMBER;
    }
}
