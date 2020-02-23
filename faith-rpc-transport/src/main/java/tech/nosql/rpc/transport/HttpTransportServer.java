package tech.nosql.rpc.transport;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author faith.huan 2020-02-20 20:47
 */
@Slf4j
public class HttpTransportServer implements TransportServer {
    private RequestHandler requestHandler;
    private Server server;

    @Override
    public void init(int port, RequestHandler requestHandler) {
        this.requestHandler = requestHandler;
        this.server = new Server(port);

        ServletContextHandler ctx = new ServletContextHandler();
        server.setHandler(ctx);

        ServletHolder holder = new ServletHolder(new RequestServlet());
        ctx.addServlet(holder, "/*");
    }

    @Override
    public void start() {
        try {
            server.start();
            server.join();
        } catch (Exception e) {
            log.error("启动异常", e);
        }
    }

    @Override
    public void stop() {
        try {
            server.stop();
        } catch (Exception e) {
            log.error("停止异常", e);
        }
    }

    class RequestServlet extends HttpServlet {
        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            ServletInputStream receive = req.getInputStream();
            ServletOutputStream response = resp.getOutputStream();
            byte[] bytes = IOUtils.readFully(receive, receive.available());
            byte[] bs =  requestHandler.onRequest(bytes);
            response.write(bs);
            response.flush();
        }
    }

    /*private void onRequest(){
        (receive, response) -> {
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

        }
    }*/
}
