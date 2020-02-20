package tech.nosql.rpc.example;

/**
 * @author faith.huan 2020-02-20 22:30
 */
public class CalServiceImpl implements CalService {
    @Override
    public int add(int a, int b) {
        return a + b;
    }

    @Override
    public int minus(int a, int b) {
        return a - b;
    }
}
