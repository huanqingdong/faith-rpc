package tech.nosql.rcp.server;

import lombok.Data;
import tech.nosql.rpc.codec.Decoder;
import tech.nosql.rpc.codec.Encoder;
import tech.nosql.rpc.codec.JsonDecoder;
import tech.nosql.rpc.codec.JsonEncoder;
import tech.nosql.rpc.transport.HttpTransportServer;
import tech.nosql.rpc.transport.TransportServer;

/**
 * 配置类
 *
 * @author faith.huan 2020-02-20 21:06
 */
@Data
public class RpcServerConfig {
    private Class<? extends TransportServer> transportClass = HttpTransportServer.class;
    private Class<? extends Encoder> encoderClass = JsonEncoder.class;
    private Class<? extends Decoder> decoderClass = JsonDecoder.class;
    private int port = 3000;
}
