package com.znsd.netty.protocol.response;

import com.znsd.netty.protocol.Packet;
import com.znsd.netty.protocol.command.Command;
import lombok.Data;

/**
 * @ClassName LogOutResponsePacket
 * @Author HETAO
 * @Date 2021/5/19 17:54
 */
@Data
public class LogOutResponsePacket extends Packet {
    private String userId;
    private String userName;
    private boolean success;
    private String reason;


    @Override
    public Byte getCommand() {
        return Command.LOGOUT_RESPONSE;
    }
}
