package tech.nosql.rpc.example;

import tech.nosql.rcp.server.RpcServer;
import tech.nosql.rcp.server.RpcServerConfig;
import tech.nosql.rpc.transport.NettyTcpTransportServer;

/**
 * @author faith.huan 2020-02-20 22:29
 */
public class Server {
    public static void main(String[] args) {
        RpcServerConfig config = new RpcServerConfig();
        config.setTransportClass(NettyTcpTransportServer.class);
        RpcServer server = new RpcServer(config);
        server.register(CalService.class,new CalServiceImpl());
        server.start();
    }
}
