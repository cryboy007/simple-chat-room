package com.znsd.netty.protocol.command;

/**
 * @InterfaceName command
 * @Author HETAO
 * @Date 2021/5/19 10:17
 */
public interface Command {
    Byte LOGIN_REQUEST = 1;
    Byte LOGIN_RESPONSE = 2;
    Byte MESSAGE_REQUEST = 3;
    Byte MESSAGE_RESPONSE = 4;
    Byte LOGOUT_REQUEST = 5;
    Byte LOGOUT_RESPONSE = 6;
    Byte CREATE_GROUP_REQUEST = 7;
    Byte CREATE_GROUP_RESPONSE = 8;
    Byte QUIT_GROUP_REQUEST = 9;
    Byte QUIT_GROUP_RESPONSE = 10;
    Byte QUERY_GROUP_MEMBER = 11;
    Byte ERROR_EXCEPTION = 12;
    Byte HEART_BEAT_REQUEST = 13;
    Byte HEART_BEAT_RESPONSE = 14;
}
