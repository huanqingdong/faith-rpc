package tech.nosql.rpc.protocol;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 网络端点
 *
 * @author faith.huan 2020-02-18 22:27
 */
@Data
@AllArgsConstructor
public class Peer {

    private String host;
    private Integer port;

}
