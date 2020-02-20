package tech.nosql.rpc.client;

import lombok.Data;
import tech.nosql.rpc.codec.Decoder;
import tech.nosql.rpc.codec.Encoder;
import tech.nosql.rpc.codec.JsonDecoder;
import tech.nosql.rpc.codec.JsonEncoder;
import tech.nosql.rpc.protocol.Peer;
import tech.nosql.rpc.transport.HttpTransportClient;
import tech.nosql.rpc.transport.TransportClient;

import java.util.Arrays;
import java.util.List;

/**
 * @author faith.huan 2020-02-20 21:59
 */
@Data
public class RpcClientConfig {
    private Class<? extends TransportClient> transportClientClass = HttpTransportClient.class;
    private Class<? extends Encoder> encoderClass = JsonEncoder.class;
    private Class<? extends Decoder> decoderClass = JsonDecoder.class;
    private Class<? extends TransportSelector> selectorClass = RandomTransportSelector.class;
    private int connCountPerPeer = 1 ;
    private List<Peer> servers = Arrays.asList(
            new Peer("127.0.0.1",3000)
    );

}
