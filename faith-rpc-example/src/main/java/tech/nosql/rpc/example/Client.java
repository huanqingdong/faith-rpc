package tech.nosql.rpc.example;

import lombok.extern.slf4j.Slf4j;
import tech.nosql.rpc.client.RpcClient;
import tech.nosql.rpc.client.RpcClientConfig;
import tech.nosql.rpc.transport.NettyTcpTransportClient;

/**
 * @author faith.huan 2020-02-20 22:28
 */
@Slf4j
public class Client {

    public static void main(String[] args) {
        RpcClientConfig config = new RpcClientConfig();
        config.setTransportClientClass(NettyTcpTransportClient.class);
        RpcClient client = new RpcClient(config);
        CalService service = client.getProxy(CalService.class);
        int add = service.add(1, 2);
        log.info("add:{}", add);
        int minus = service.minus(10, 2);
        log.info("minus:{}", minus);

        client.close();
    }
}
