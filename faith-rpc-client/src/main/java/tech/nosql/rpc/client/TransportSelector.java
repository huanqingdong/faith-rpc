package tech.nosql.rpc.client;

import tech.nosql.rpc.protocol.Peer;
import tech.nosql.rpc.transport.TransportClient;

import java.util.List;

/**
 * 表示选择哪个服务器去连接
 *
 * @author faith.huan 2020-02-20 21:43
 */
public interface TransportSelector {

    void init(List<Peer> peers, int count,Class<? extends TransportClient> transportClientClass);

    /**
     * 选择一个transport与server交互
     *
     * @return client
     */
    TransportClient select();

    void release(TransportClient client);

    void close();

}
