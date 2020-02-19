package tech.nosql.rpc.common;

/**
 * 测试类，分别有私有，保护，公有三个方法
 *
 * @author faith.huan 2020-02-19 20:47
 */
public class TestClass {

    private String pri() {
        return "pri";
    }

    protected String pro() {
        return "pro";
    }

    public String pub() {
        return "pub";
    }

}
