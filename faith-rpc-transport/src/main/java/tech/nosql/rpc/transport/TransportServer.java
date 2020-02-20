package tech.nosql.rpc.transport;

/**
 * 1. 启动监听端口
 * 2. 处理请求
 * 3. 关闭监听
 *
 * @author faith.huan 2020-02-20 20:42
 */
public interface TransportServer {
    void init(int port, RequestHandler requestHandler);

    void start();

    void stop();
}
