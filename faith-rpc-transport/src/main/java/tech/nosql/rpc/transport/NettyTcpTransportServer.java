package tech.nosql.rpc.transport;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author faith.huan 2020-02-23 21:13
 */
@Slf4j
public class NettyTcpTransportServer implements TransportServer {

    private ChannelFuture channelFuture;


    @Override
    public void init(int port, RequestHandler requestHandler) {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline p = ch.pipeline();

                            p.addLast(
                                    new ObjectEncoder(),
                                    new ObjectDecoder(ClassResolvers.cacheDisabled(null)),
                                    new ObjectEchoServerHandler(requestHandler));
                        }
                    });

            // Bind and start to accept incoming connections.
            channelFuture = b.bind(port).sync();
            // channelFuture.channel().closeFuture().sync();
            log.info("init finish, channelFuture:{}", channelFuture);
        } catch (Exception e) {
            throw new IllegalStateException("初始化server发生异常", e);
        } finally {
           /* bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();*/
        }
    }

    @Override
    public void start() {
        try {
            channelFuture.channel().closeFuture().sync();
            log.info("start server ...");
        } catch (Exception e) {
            log.info("启动server发生异常",e);
        }
    }

    @Override
    public void stop() {

    }

    public class ObjectEchoServerHandler extends ChannelInboundHandlerAdapter {

        private RequestHandler requestHandler;

        public ObjectEchoServerHandler(RequestHandler requestHandler) {
            this.requestHandler = requestHandler;
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) {
            if (msg instanceof byte[]) {
                byte[] res = requestHandler.onRequest((byte[]) msg);
                ctx.writeAndFlush(res);
            } else {
                System.out.println("服务器接收到msg = " + msg);
                ctx.writeAndFlush("不支持消息类型" + msg.getClass().getName());
            }
        }

        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) {
            ctx.flush();
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            cause.printStackTrace();
            ctx.close();
        }
    }
}
