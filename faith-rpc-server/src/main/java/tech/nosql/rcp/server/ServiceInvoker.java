package tech.nosql.rcp.server;

import tech.nosql.rpc.common.ReflectionUtils;
import tech.nosql.rpc.protocol.Request;

/**
 * 服务调用
 *
 * @author faith.huan 2020-02-20 21:29
 */
public class ServiceInvoker {

    public Object invoke(ServiceInstance serviceInstance,
                         Request request) {
        return ReflectionUtils.invoke(serviceInstance.getTarget(), serviceInstance.getMethod(),
                request.getParameters());
    }

}
