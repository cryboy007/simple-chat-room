package com.znsd.netty.protocol;

import com.znsd.netty.protocol.command.Command;
import com.znsd.netty.protocol.request.*;
import com.znsd.netty.protocol.response.*;
import com.znsd.netty.serialize.Serializer;
import com.znsd.netty.serialize.SerializerAlgorithm;
import com.znsd.netty.serialize.impl.JSONSerializer;
import com.znsd.netty.server.handler.MessageRequestHandler;
import io.netty.buffer.ByteBuf;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import static com.znsd.netty.protocol.command.Command.*;

/**
 * @ClassName PacketCodec
 * @Author HETAO
 * @Date 2021/5/19 10:23
 */
public class PacketCodec {

    public static final int MAGIC_NUMBER = 0x9527;
    public static final PacketCodec INSTANCE = new PacketCodec();

    private final Map<Byte, Class<? extends Packet>> packetTypeMap;
    private final Map<Byte, Serializer> serializerMap;


    private PacketCodec() {
        packetTypeMap = new HashMap<>();
        packetTypeMap.put(LOGIN_REQUEST, LoginRequestPacket.class);
        packetTypeMap.put(LOGIN_RESPONSE, LoginResponsePacket.class);
        packetTypeMap.put(MESSAGE_REQUEST, MessageRequestPacket.class);
        packetTypeMap.put(MESSAGE_RESPONSE, MessageResponsePacket.class);
        packetTypeMap.put(LOGOUT_REQUEST, LogOutRequestPacket.class);
        packetTypeMap.put(LOGOUT_RESPONSE, LogOutResponsePacket.class);
        packetTypeMap.put(CREATE_GROUP_REQUEST, CreateGroupRequestPacket.class);
        packetTypeMap.put(CREATE_GROUP_RESPONSE, CreateGroupResponsePacket.class);
        packetTypeMap.put(QUIT_GROUP_REQUEST, QuitGroupRequestPacket.class);
        packetTypeMap.put(QUIT_GROUP_RESPONSE, QuitGroupResponsePacket.class);
        packetTypeMap.put(QUERY_GROUP_MEMBER,QueryGroupRequestPacket.class);
        packetTypeMap.put(ERROR_EXCEPTION,ExceptionResponsePacket.class);
        packetTypeMap.put(HEART_BEAT_REQUEST,HeartBeatRequestPacket.class);
        packetTypeMap.put(HEART_BEAT_RESPONSE,HeartBeatResponsePacket.class);

        serializerMap = new HashMap<>();
        Serializer serializer = new JSONSerializer();
        serializerMap.put(serializer.getSerializerAlgorithm(), serializer);
    }

    public void encode(ByteBuf byteBuf, Packet packet) {
        // 1. ????????? java ??????
        byte[] bytes = Serializer.DEFAULT.serialize(packet);

        // 2. ??????????????????
        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlgorithm());
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);
    }


    public Packet decode(ByteBuf byteBuf) {
        // ?????? magic number
        byteBuf.skipBytes(4);

        // ???????????????
        byteBuf.skipBytes(1);

        // ???????????????
        byte serializeAlgorithm = byteBuf.readByte();

        // ??????
        byte command = byteBuf.readByte();

        // ???????????????
        int length = byteBuf.readInt();

        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);
        //??????????????????????????????response?????????
        Class<? extends Packet> requestType = getRequestType(command);
        //??????????????????????????????
        Serializer serializer = getSerializer(serializeAlgorithm);

        if (requestType != null && serializer != null) {
            return serializer.deserialize(requestType, bytes);
        }

        return null;
    }

    private Serializer getSerializer(byte serializeAlgorithm) {

        return serializerMap.get(serializeAlgorithm);
    }

    private Class<? extends Packet> getRequestType(byte command) {

        return packetTypeMap.get(command);
    }
}
