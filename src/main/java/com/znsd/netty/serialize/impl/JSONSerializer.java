package com.znsd.netty.serialize.impl;

import com.alibaba.fastjson.JSONObject;
import com.znsd.netty.serialize.Serializer;
import com.znsd.netty.serialize.SerializerAlgorithm;

/**
 * @ClassName JSONSerializer
 * @Author HETAO
 * @Date 2021/5/19 10:31
 */
public class JSONSerializer implements Serializer {
    @Override
    public byte getSerializerAlgorithm() {
        return SerializerAlgorithm.JSON;
    }

    @Override
    public byte[] serialize(Object object) {
        return JSONObject.toJSONBytes(object);
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        return JSONObject.parseObject(bytes,clazz);
    }
}
