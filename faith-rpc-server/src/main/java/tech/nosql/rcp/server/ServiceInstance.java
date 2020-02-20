package tech.nosql.rcp.server;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.lang.reflect.Method;

/**
 * 服务实例
 *
 * @author faith.huan 2020-02-20 21:08
 */
@Data
@AllArgsConstructor
public class ServiceInstance {

    private Object target;
    private Method method;
}
