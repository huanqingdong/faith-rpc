package tech.nosql.rpc.transport;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * 处理请求
 *
 * @author faith.huan 2020-02-20 20:45
 */
public interface RequestHandler {

    void onRequest(InputStream receive, OutputStream response);

}
