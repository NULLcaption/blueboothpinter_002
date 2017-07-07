package com.cxg.myapplication;

import com.cxg.myapplication.utils.WebServiceUtils;

import org.junit.Test;

import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void init () {
        HashMap<String,String> properties = new HashMap<>();
        properties.put("IZkurno","0462");
        List<Object> list = WebServiceUtils.callWebServiceFor001(WebServiceUtils.URL_001, WebServiceUtils.METHOD_NAME_001, properties);
        if (list.size() != 0){
            System.out.println(list.get(1));
        }
    }
    //20300001
    @Test
    public void init1 () {
        HashMap<String,String> properties = new HashMap<>();
        properties.put("IMatnr","20300001");
        List<Object> list = WebServiceUtils.callWebServiceFor001(WebServiceUtils.URL_001, WebServiceUtils.METHOD_NAME_001, properties);
        if (list.size() != 0){
            System.out.println(list.get(0));
        }
    }
}