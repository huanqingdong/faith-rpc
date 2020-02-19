package tech.nosql.rpc.common;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.Assert.*;

/**
 * @author faith.huan 2020-02-19 20:47
 */
public class ReflectionUtilsTest {

    @Test
    public void newInstance() {
        TestClass testClass = ReflectionUtils.newInstance(TestClass.class);
        assertNotNull(testClass);
    }


    @Test
    public void getPublicMethods() {
        Method[] publicMethods = ReflectionUtils.getPublicMethods(TestClass.class);
        assertEquals(1, publicMethods.length);
        assertEquals("pub", publicMethods[0].getName());
    }

    @Test
    public void invoke() throws InvocationTargetException, IllegalAccessException {
        Method[] publicMethods = ReflectionUtils.getPublicMethods(TestClass.class);
        Object result = publicMethods[0].invoke(new TestClass());
        assertEquals("pub", result);
    }
}