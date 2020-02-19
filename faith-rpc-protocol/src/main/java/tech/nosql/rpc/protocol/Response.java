package tech.nosql.rpc.protocol;

/**
 * 响应类
 *
 * @author faith.huan 2020-02-19 20:34
 */
public class Response {
    /**
     * 状态码 0：成功，非0 失败
     */
    private Integer code;
    private String message = "执行成功";
    /**
     * 返回数据
     */
    private Object data;

}
