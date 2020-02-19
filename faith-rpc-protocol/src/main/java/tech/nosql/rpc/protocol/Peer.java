package tech.nosql.rpc.protocol;

import lombok.Data;

/**
 * 网络端点
 *
 * @author faith.huan 2020-02-18 22:27
 */
@Data
public class Peer {

    private String host;
    private Integer port;

}
