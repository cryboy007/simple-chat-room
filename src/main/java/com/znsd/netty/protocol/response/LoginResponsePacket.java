package com.znsd.netty.protocol.response;

import com.znsd.netty.protocol.Packet;
import com.znsd.netty.protocol.command.Command;
import lombok.Data;

/**
 * @ClassName LoginResponsePacket
 * @Author HETAO
 * @Date 2021/5/19 11:13
 */
@Data
public class LoginResponsePacket extends Packet {
    private String userId;

    private String userName;

    private boolean success;

    private String reason;


    @Override
    public Byte getCommand() {
        return Command.LOGIN_RESPONSE;
    }
}
