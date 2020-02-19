package tech.nosql.rpc.codec;

import com.alibaba.fastjson.JSON;

/**
 * json编码器
 *
 * @author faith.huan 2020-02-19 21:01
 */
public class JsonDecoder implements Decoder {

    @Override
    public <T> T decode(byte[] bytes, Class<T> clazz) {

        return JSON.parseObject(bytes, clazz);
    }
}
