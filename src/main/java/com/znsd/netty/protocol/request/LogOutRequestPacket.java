package com.znsd.netty.protocol.request;

import com.znsd.netty.protocol.Packet;
import com.znsd.netty.protocol.command.Command;
import lombok.Data;

/**
 * @ClassName LogOutRequestPacket
 * @Author HETAO
 * @Date 2021/5/19 17:50
 */
@Data
public class LogOutRequestPacket extends Packet {
    private String userId;
    private String userName;
    @Override
    public Byte getCommand() {
        return Command.LOGOUT_REQUEST;
    }
}
