package tech.nosql.rpc.codec;

/**
 * 序列化接口
 *
 * @author faith.huan 2020-02-19 20:53
 */
public interface Encoder {

    /**
     * 编码
     *
     * @param obj 待编码对象
     * @return 编码后字节
     */
    byte[] encode(Object obj);
}
