package com.znsd.netty.protocol.response;

import com.znsd.netty.protocol.Packet;
import com.znsd.netty.protocol.command.Command;
import lombok.Data;

/**
 * @ClassName ExceptionResponsePacket
 * @Author HETAO
 * @Date 2021/5/20 14:31
 */
@Data
public class ExceptionResponsePacket extends Packet {
    private String message;

    @Override
    public Byte getCommand() {
        return Command.ERROR_EXCEPTION;
    }
}
