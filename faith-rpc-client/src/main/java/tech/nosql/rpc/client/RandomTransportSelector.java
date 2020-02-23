package tech.nosql.rpc.client;

import lombok.extern.slf4j.Slf4j;
import tech.nosql.rpc.common.ReflectionUtils;
import tech.nosql.rpc.protocol.Peer;
import tech.nosql.rpc.transport.TransportClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 随机服务选择器
 *
 * @author faith.huan 2020-02-20 21:48
 */
@Slf4j
public class RandomTransportSelector implements TransportSelector {

    List<TransportClient> clients = new ArrayList<>();

    @Override
    public synchronized void init(List<Peer> peers, int count,
                                  Class<? extends TransportClient> transportClientClass) {
        final int cnt = Math.max(count, 1);
        peers.forEach((p) -> {
            for (int i = 0; i < cnt; i++) {
                TransportClient client = ReflectionUtils.newInstance(transportClientClass);
                client.connect(p);
                clients.add(client);
            }
            log.info("connect server :{}", p);
        });
    }

    @Override
    public synchronized TransportClient select() {
        int i = new Random().nextInt(clients.size());
        return clients.remove(i);
    }

    @Override
    public synchronized void release(TransportClient client) {
        if (client != null) {
            clients.add(client);
        }
    }

    @Override
    public synchronized void close() {
        clients.forEach(TransportClient::close);
        clients.clear();
    }
}
