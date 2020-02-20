package tech.nosql.rpc.transport;

import tech.nosql.rpc.protocol.Peer;

import java.io.InputStream;

/**
 * 1. 创建连接
 * 2. 发送数据,等待响应
 * 3. 关闭连接
 *
 * @author faith.huan 2020-02-20 20:42
 */
public interface TransportClient {

    void connect(Peer peer);

    InputStream write(InputStream inputStream);

    void close();
}
