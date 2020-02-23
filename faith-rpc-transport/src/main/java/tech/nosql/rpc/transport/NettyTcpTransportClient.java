package tech.nosql.rpc.transport;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import tech.nosql.rpc.protocol.Peer;

import java.util.concurrent.TimeUnit;

/**
 * @author faith.huan 2020-02-23 18:57
 */
public class NettyTcpTransportClient implements TransportClient {

    private Channel channel;
    private ClientHandler clientHandler = new ClientHandler();

    @Override
    public void connect(Peer peer) {

        new Thread(() -> {
            EventLoopGroup group = new NioEventLoopGroup();
            try {
                Bootstrap b = new Bootstrap();
                b.group(group)
                        .channel(NioSocketChannel.class)
                        .handler(new ChannelInitializer<SocketChannel>() {
                            @Override
                            public void initChannel(SocketChannel ch) throws Exception {
                                ch.pipeline().addLast(
                                        new ObjectEncoder(),
                                        new ObjectDecoder(ClassResolvers.cacheDisabled(null)),
                                        clientHandler
                                );
                            }
                        });

                // Start the connection attempt.
                channel = b.connect(peer.getHost(), peer.getPort()).sync().channel();
                channel.closeFuture().sync();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                group.shutdownGracefully();
            }
        }).start();

        while (channel == null || !channel.isActive()) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public byte[] write(byte[] data) {
        ChannelPromise channelPromise = clientHandler.sendMessage(data);

        try {
            channelPromise.await(3, TimeUnit.SECONDS);
            return (byte[]) clientHandler.getResponse();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


        /*ChannelFuture future = channel.write(inputStream);
        channel.flush();
        ValHolder<Object> valHolder = new ValHolder<>();
        try {
            CountDownLatch latch = new CountDownLatch(1);
            future.addListener(new GenericFutureListener<Future<? super Void>>() {
                @Override
                public void operationComplete(Future<? super Void> future) throws Exception {
                    valHolder.setVal(future.get());
                    latch.countDown();
                }
            }).sync();
            latch.await();
            return (InputStream) valHolder.getVal();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }*/
    }

    @Override
    public void close() {
        try {
            channel.close().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Data
    static class ValHolder<T> {

        public T val;

    }

    @Slf4j
    public static class ClientHandler extends ChannelInboundHandlerAdapter {


        private ChannelHandlerContext ctx;
        private ChannelPromise promise;
        private Object response;

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            super.channelActive(ctx);
            this.ctx = ctx;
            ctx.writeAndFlush("hello");
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            if(msg instanceof byte[]){
                response = msg;
                promise.setSuccess();
            }else {
                log.info("客户端收到" + msg);
            }
        }

        public synchronized ChannelPromise sendMessage(Object message) {
            while (ctx == null) {
                try {
                    TimeUnit.MILLISECONDS.sleep(1);
                    //logger.error("等待ChannelHandlerContext实例化");
                } catch (InterruptedException e) {
                    log.error("等待ChannelHandlerContext实例化过程中出错", e);
                }
            }
            promise = ctx.newPromise();
            ctx.writeAndFlush(message);
            return promise;
        }

        public Object getResponse() {
            return response;
        }

    }
}
