package tech.nosql.rcp.server;

/**
 * @author faith.huan 2020-02-20 21:24
 */
public class TestInterfaceImpl implements TestInterface {
    @Override
    public void hello() {
        System.out.println("Hello");
    }
}
