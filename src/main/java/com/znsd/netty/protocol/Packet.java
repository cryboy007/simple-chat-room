package com.znsd.netty.protocol;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @ClassName Packet
 * @Author HETAO
 * @Date 2021/5/19 10:18
 */
@Data
public abstract class Packet {

    @JSONField(serialize = false)
    private Byte version = 1;

    @JSONField(serialize = false)
    public abstract Byte getCommand();
}
