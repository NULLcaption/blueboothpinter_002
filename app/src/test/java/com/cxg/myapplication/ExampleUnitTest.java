package com.cxg.myapplication;

import com.cxg.myapplication.pojo.Ztwm004;
import com.cxg.myapplication.utils.WebServiceUtils;

import org.junit.Test;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        properties.put("IZkurno","0384");
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

    @Test
    public void init2() {
        Map<String, String> map = WebServiceUtils.callWebServiceFor004(WebServiceUtils.URL_004, WebServiceUtils.METHOD_NAME_004);
        System.out.println(map);
    }

    @Test
    public void init3() throws ParseException {
        //请求的参数对象
        Ztwm004 properties = new Ztwm004();

        properties.setMatnr("10400001");
        properties.setMeins("BOX");//单位
        properties.setMenge("80.00");//数量
        properties.setWerks("1000");
        properties.setZbc("1");
        properties.setZgrdate("2017-06-27");
        properties.setZkurno("0462");
        properties.setZlinecode("00");
        properties.setZproddate("2017-06-27");

        //通过webservice获取到的返回值
        List<Object> list = WebServiceUtils.callWebServiceFor002(WebServiceUtils.URL_002, WebServiceUtils.METHOD_NAME_002, properties);

        if (list.size()!=0){
            for (int i =0 ;i<list.size();i++){
                System.out.println(list.get(i));
            }
        }
    }

    @Test
    public void init4() throws ParseException {
        Ztwm004 properties = new Ztwm004();
        properties.setILgmng("30");
        properties.setMatnr("10400001");
        properties.setMeins("BOX");
        properties.setMenge("2");
        properties.setWerks("2000");
        properties.setZbc("1");
        properties.setZgrdate("2017-06-27");
        properties.setZlinecode("00");
        properties.setIZlocco("0462");
        properties.setZproddate("2017-06-27");
        properties.setItZipcode(null);
        List<Object> list = WebServiceUtils.callWebServiceFor002(WebServiceUtils.URL_007, WebServiceUtils.METHOD_NAME_007, properties);
        if (list.size() != 0) {
            for (int i = 0; i < list.size(); i++) {
                Ztwm004 ztwm004 = (Ztwm004)list.get(i);
                System.out.println(ztwm004);
            }
        }
    }
}