package tech.nosql.rpc.common;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

/**
 * 反射工具类
 *
 * @author faith.huan 2020-02-19 20:37
 */
public class ReflectionUtils {

    public static <T> T newInstance(Class<T> clazz) {
        try {
            return clazz.newInstance();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * 获取公共方法
     */
    public static Method[] getPublicMethods(Class clazz) {
        return Arrays.stream(clazz.getDeclaredMethods())
                .filter((m) -> Modifier.isPublic(m.getModifiers()))
                .toArray(Method[]::new);
    }


    public static Object invoke(Object tar, Method method, Object... args) {
        try {
            return method.invoke(tar, args);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }

    }
}
