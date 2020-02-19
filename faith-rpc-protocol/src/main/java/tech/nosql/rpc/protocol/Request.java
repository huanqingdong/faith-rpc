package tech.nosql.rpc.protocol;

import lombok.Data;

/**
 * Rpc请求
 *
 * @author faith.huan 2020-02-19 20:34
 */
@Data
public class Request {
    private ServiceDescriptor serviceDescriptor;
    private Object[] parameters;
}
