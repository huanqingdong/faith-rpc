package tech.nosql.rpc.codec;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author faith.huan 2020-02-19 21:46
 */
public class JsonDecoderTest {

    @Test
    public void decode() {

        TestBean testBean = new TestBean();
        testBean.setAge(12);
        testBean.setName("faith");
        byte[] bytes = new JsonEncoder().encode(testBean);

        JsonDecoder jsonDecoder = new JsonDecoder();
        TestBean bean = jsonDecoder.decode(bytes, TestBean.class);

        assertEquals(bean.getAge().intValue(),12);
        assertEquals(bean.getName(),"faith");

    }
}