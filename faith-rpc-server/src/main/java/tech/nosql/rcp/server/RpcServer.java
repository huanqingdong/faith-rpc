package tech.nosql.rcp.server;

import lombok.extern.slf4j.Slf4j;
import tech.nosql.rpc.codec.Decoder;
import tech.nosql.rpc.codec.Encoder;
import tech.nosql.rpc.common.ReflectionUtils;
import tech.nosql.rpc.protocol.Request;
import tech.nosql.rpc.protocol.Response;
import tech.nosql.rpc.transport.TransportServer;

/**
 * @author faith.huan 2020-02-20 21:31
 */
@Slf4j
public class RpcServer {

    private TransportServer transportServer;
    private Encoder encoder;
    private Decoder decoder;
    private ServiceManager serviceManager;
    private ServiceInvoker serviceInvoker;

    public RpcServer() {
        this(new RpcServerConfig());
    }

    public RpcServer(RpcServerConfig config) {
        // codec
        this.encoder = ReflectionUtils.newInstance(config.getEncoderClass());
        this.decoder = ReflectionUtils.newInstance(config.getDecoderClass());
        // service
        this.serviceManager = new ServiceManager();
        this.serviceInvoker = new ServiceInvoker();

        // transportServer
        this.transportServer = ReflectionUtils.newInstance(config.getTransportClass());
        this.transportServer.init(config.getPort(), (bytes) -> {
            Response resp = new Response();
            try {
                Request request = decoder.decode(bytes, Request.class);
                log.info("get request:{}", request);
                ServiceInstance serviceInstance = serviceManager.lookup(request);
                Object res = serviceInvoker.invoke(serviceInstance, request);
                resp.setData(res);
            } catch (Exception e) {
                log.error("服务器异常", e);
                resp.setCode(1);
                resp.setMessage("服务器异常" + e.getMessage());
            }
            return encoder.encode(resp);

        });


    }

    public void start() {
        this.transportServer.start();
    }

    public void stop() {
        this.transportServer.stop();
    }

    public <T> void register(Class<T> interfaceClass, T bean) {
        serviceManager.register(interfaceClass, bean);
    }

}
