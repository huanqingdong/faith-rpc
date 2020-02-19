package tech.nosql.rpc.codec;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author faith.huan 2020-02-19 21:48
 */
public class JsonEncoderTest {

    @Test
    public void encode() {
        TestBean testBean = new TestBean();
        testBean.setAge(12);
        testBean.setName("faith");
        byte[] encode = new JsonEncoder().encode(testBean);
        assertNotNull(encode);
    }
}