package tech.nosql.rpc.codec;

import com.alibaba.fastjson.JSON;

/**
 * json编码器
 *
 * @author faith.huan 2020-02-19 21:01
 */
public class JsonEncoder implements Encoder{
    @Override
    public byte[] encode(Object obj) {
        return JSON.toJSONBytes(obj);
    }
}
