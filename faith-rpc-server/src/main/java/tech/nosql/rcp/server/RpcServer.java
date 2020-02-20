package tech.nosql.rcp.server;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import tech.nosql.rpc.codec.Decoder;
import tech.nosql.rpc.codec.Encoder;
import tech.nosql.rpc.common.ReflectionUtils;
import tech.nosql.rpc.protocol.Request;
import tech.nosql.rpc.protocol.Response;
import tech.nosql.rpc.transport.TransportServer;

import java.io.IOException;

/**
 * @author faith.huan 2020-02-20 21:31
 */
@Slf4j
public class RpcServer {

    private RpcServerConfig config;
    private TransportServer net;
    private Encoder encoder;
    private Decoder decoder;
    private ServiceManager serviceManager;
    private ServiceInvoker serviceInvoker;

    public RpcServer(){
        this(new RpcServerConfig());
    }

    public RpcServer(RpcServerConfig config) {
        this.config = config;
        // net
        this.net = ReflectionUtils.newInstance(config.getTransportClass());
        this.net.init(config.getPort(), (receive, response) -> {
            Response resp = new Response();
            try {
                byte[] bytes = IOUtils.readFully(receive, receive.available());
                Request request = decoder.decode(bytes, Request.class);
                log.info("get request:{}", request);
                ServiceInstance serviceInstance = serviceManager.lookup(request);
                Object res = serviceInvoker.invoke(serviceInstance, request);
                resp.setData(res);
            } catch (Exception e) {
                log.error("服务器异常",e);
                resp.setCode(1);
                resp.setMessage("服务器异常"+e.getMessage());
            }finally {
                byte[] encode = encoder.encode(resp);
                try {
                    response.write(encode);
                } catch (IOException e) {
                    log.error("回写客户端失败");
                }
            }

        });
        // codec
        this.encoder = ReflectionUtils.newInstance(config.getEncoderClass());
        this.decoder = ReflectionUtils.newInstance(config.getDecoderClass());
        // service
        this.serviceManager = new ServiceManager();
        this.serviceInvoker = new ServiceInvoker();

    }

    public void start() {
        this.net.start();
    }

    public void stop() {
        this.net.stop();
    }

    public <T> void register(Class<T> interfaceClass, T bean) {
        serviceManager.register(interfaceClass, bean);
    }

}
