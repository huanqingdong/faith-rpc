package tech.nosql.rpc.example;

import lombok.extern.slf4j.Slf4j;
import tech.nosql.rpc.client.RpcClient;

/**
 * @author faith.huan 2020-02-20 22:28
 */
@Slf4j
public class Client {

    public static void main(String[] args) {
        RpcClient client = new RpcClient();
        CalService service = client.getProxy(CalService.class);
        int add = service.add(1, 2);
        log.info("add:{}", add);
        int minus = service.minus(10, 2);
        log.info("minus:{}", minus);
    }
}
