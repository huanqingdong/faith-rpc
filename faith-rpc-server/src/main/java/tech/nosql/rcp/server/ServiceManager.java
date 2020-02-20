package tech.nosql.rcp.server;

import lombok.extern.slf4j.Slf4j;
import tech.nosql.rpc.common.ReflectionUtils;
import tech.nosql.rpc.protocol.Request;
import tech.nosql.rpc.protocol.ServiceDescriptor;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 服务管理类
 *
 * @author faith.huan 2020-02-20 21:09
 */
@Slf4j
public class ServiceManager {

    private Map<ServiceDescriptor, ServiceInstance> services = new ConcurrentHashMap<>();

    public <T> void register(Class<T> interfaceClass, T bean) {
        Method[] publicMethods = ReflectionUtils.getPublicMethods(interfaceClass);
        for (Method publicMethod : publicMethods) {
            ServiceDescriptor descriptor = ServiceDescriptor.from(interfaceClass, publicMethod);
            ServiceInstance instance = new ServiceInstance(bean, publicMethod);
            services.put(descriptor, instance);
            log.info("register service:{}", descriptor);
        }
    }

    public ServiceInstance lookup(Request request) {
        ServiceDescriptor serviceDescriptor = request.getServiceDescriptor();
        return services.get(serviceDescriptor);
    }

}
