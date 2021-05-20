package com.znsd.netty.protocol.request;

import com.znsd.netty.protocol.Packet;
import com.znsd.netty.protocol.command.Command;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName MessageRequestPacket
 * @Author HETAO
 * @Date 2021/5/19 15:17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageRequestPacket extends Packet {
    private String toUserId;
    private String message;


    @Override
    public Byte getCommand() {
        return Command.MESSAGE_REQUEST;
    }
}
