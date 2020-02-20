package tech.nosql.rpc.example;

import tech.nosql.rcp.server.RpcServer;

/**
 * @author faith.huan 2020-02-20 22:29
 */
public class Server {
    public static void main(String[] args) {
        RpcServer server = new RpcServer();
        server.register(CalService.class,new CalServiceImpl());
        server.start();
    }
}
