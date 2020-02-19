package tech.nosql.rpc.codec;

/**
 * 序列化接口
 *
 * @author faith.huan 2020-02-19 20:53
 */
public interface Decoder {

    /**
     * 解码
     *
     * @param bytes 待解码字节
     * @param clazz 类
     * @return 解码后对象
     */
     <T> T decode(byte[] bytes,Class<T> clazz);
}
