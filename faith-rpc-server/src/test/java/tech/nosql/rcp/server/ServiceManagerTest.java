package tech.nosql.rcp.server;

import org.junit.Before;
import org.junit.Test;
import tech.nosql.rpc.common.ReflectionUtils;
import tech.nosql.rpc.protocol.Request;
import tech.nosql.rpc.protocol.ServiceDescriptor;

import java.lang.reflect.Method;

import static org.junit.Assert.*;

/**
 * @author faith.huan 2020-02-20 21:23
 */
public class ServiceManagerTest {

    ServiceManager sm;

    @Before
    public void before(){
        sm= new ServiceManager();
    }

    @Test
    public void register() {
        TestInterface bean = new TestInterfaceImpl();
        sm.register(TestInterface.class,bean);
    }

    @Test
    public void lookup() {
        register();
        Method publicMethod = ReflectionUtils.getPublicMethods(TestInterface.class)[0];
        Request request = new Request();
        request.setServiceDescriptor(ServiceDescriptor.from(TestInterface.class,publicMethod));
        ServiceInstance lookup = sm.lookup(request);
        assertNotNull(lookup);
    }
}