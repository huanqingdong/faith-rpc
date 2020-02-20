package tech.nosql.rpc.client;

import org.apache.commons.io.IOUtils;
import tech.nosql.rpc.codec.Decoder;
import tech.nosql.rpc.codec.Encoder;
import tech.nosql.rpc.common.ReflectionUtils;
import tech.nosql.rpc.protocol.Request;
import tech.nosql.rpc.protocol.Response;
import tech.nosql.rpc.protocol.ServiceDescriptor;
import tech.nosql.rpc.transport.TransportClient;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author faith.huan 2020-02-20 22:04
 */
public class RpcClient {
    private RpcClientConfig clientConfig;
    private Encoder encoder;
    private Decoder decoder;
    private TransportSelector selector;

    public RpcClient() {
        this(new RpcClientConfig());
    }

    public RpcClient(RpcClientConfig clientConfig) {
        this.clientConfig = clientConfig;

        this.encoder = ReflectionUtils.newInstance(clientConfig.getEncoderClass());
        this.decoder = ReflectionUtils.newInstance(clientConfig.getDecoderClass());
        this.selector = ReflectionUtils.newInstance(clientConfig.getSelectorClass());
        this.selector.init(clientConfig.getServers(),
                clientConfig.getConnCountPerPeer(),
                clientConfig.getTransportClientClass());
    }

    public <T> T getProxy(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(getClass().getClassLoader(),
                new Class[]{clazz}, new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        TransportClient client = null;
                        try {
                            client = selector.select();
                            Request request = new Request();
                            request.setServiceDescriptor(ServiceDescriptor.from(clazz, method));
                            request.setParameters(args);
                            byte[] bytes = encoder.encode(request);
                            InputStream inputStream = client.write(new ByteArrayInputStream(bytes));
                            Response response = decoder.decode(IOUtils.readFully(inputStream, inputStream.available()), Response.class);
                            if (response == null || response.getCode() != 0) {
                                throw new IllegalStateException("调用异常:" + response);
                            }
                            return response.getData();

                        } catch (Exception e) {
                            throw new IllegalStateException("调用异常:" + e.getMessage());
                        } finally {
                            selector.release(client);
                        }
                    }
                });
    }
}
