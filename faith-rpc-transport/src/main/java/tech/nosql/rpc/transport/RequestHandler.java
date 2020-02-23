package tech.nosql.rpc.transport;

/**
 * 处理请求
 *
 * @author faith.huan 2020-02-20 20:45
 */
public interface RequestHandler {

    byte[] onRequest(byte[] receive);

}
