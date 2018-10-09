package com.cxg.myapplication.utils;


import com.cxg.myapplication.pojo.Zswm003;
import com.cxg.myapplication.pojo.Ztwm004;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.net.SocketException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * description: webservice服务工具类
 * author: xg.chen
 * date: 2017/6/26 14:07
 * version: 1.0
 */
public class WebServiceUtils {

    //命名空间
    public static String NAMESPACE = "urn:sap-com:document:sap:soap:functions:mc-style";
    //请求方法名
    public static String METHOD_NAME_004 = "ZwmRfcIts004";//获取单位
    public static String METHOD_NAME_001 = "ZwmRfcIts001";//获取客流码名称和物料名称
    public static String METHOD_NAME_002 = "ZwmRfcIts002";//生成托盘编码
    public static String METHOD_NAME_007 = "ZwmRfcIts007";//生成托盘编码
    //请求路径
    public static String SOAP_ACTION_004 = NAMESPACE + "/" + METHOD_NAME_004;
    public static String SOAP_ACTION_001 = NAMESPACE + "/" + METHOD_NAME_001;
    public static String SOAP_ACTION_002 = NAMESPACE + "/" + METHOD_NAME_002;
    public static String SOAP_ACTION_007 = NAMESPACE + "/" + METHOD_NAME_007;
    //请求的webservice路径
    //正式机路径
    public static final String URL_004 = "http://sap/bc/srt/rfc/sap/zwmits4/800/zwmits4/binding";
    public static final String URL_001 = "http://sap/bc/srt/rfc/sap/zwm/800/zwm/binding";
    public static final String URL_002 = "http://sap/bc/srt/rfc/sap/zwmits2/800/zwmits2/binding";
    public static final String URL_007 = "http://sap/bc/srt/rfc/sap/zwmits7/800/zwmits7/binding";
    


    /**
     * 请求ZwmRfcIts002接口生成托盘编码
     * @param url 请求路径
     * @param methodName 请求方法
     * @param properties 请求参数
     * @return List
     */
    public static List<Object> callWebServiceFor002(String url, String methodName, Ztwm004 properties) throws ParseException {
        //返回的结果集
        List<Object> resultList = new ArrayList<>();
        // set up
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        // SoapObject添加参数
        request.addProperty("ILgmng",properties.getILgmng());
        request.addProperty("IMatnr",properties.getMatnr());
        request.addProperty("IMeins",properties.getMeins());//单位
        request.addProperty("IMenge",properties.getMenge());//数量
        request.addProperty("IWerks",properties.getWerks());//工厂
        request.addProperty("IZbc",properties.getZbc());//班别
        request.addProperty("IZgrdate",properties.getZgrdate());//生产日期
        request.addProperty("IZline",properties.getZlinecode());//线别
        request.addProperty("IZlocco",properties.getIZlocco());//客流码
        request.addProperty("IZproddate",properties.getZproddate());//库存日期
        request.addProperty("ItZipcode",properties.getItZipcode());

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
        envelope.dotNet = false;
        envelope.setOutputSoapObject(request);

        HttpTransportSE httpTransport = new HttpTransportSE(url);
        httpTransport.debug = true;
        try {
            httpTransport.call(SOAP_ACTION_007, envelope);
            if (envelope.bodyIn instanceof SoapObject) {
                SoapObject soapObject = (SoapObject) envelope.bodyIn;
                //解析后的返回list
                resultList = parseSoapObject007(soapObject);
                return resultList;
            } else if (envelope.bodyIn instanceof SoapFault) {
                SoapFault soapFault = (SoapFault) envelope.bodyIn;
                try {
                    throw new Exception(soapFault.getMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultList;
    }

    /**
     * Soap Object解析返回值
     * @param result 返回值
     * @return List
     */
    public static List<Object> parseSoapObject007(SoapObject result) {
        List<Object> list = new ArrayList<>();

        String EZcupno = result.getProperty("EZcupno").toString();//批次编号
        SoapObject provinceSoapObject1 = (SoapObject) result.getProperty("ItZipcode");//托盘编码
        if (provinceSoapObject1 == null) {
            return null;
        }
        for (int i = 0; i < provinceSoapObject1.getPropertyCount(); i++) {
            SoapObject soapObject = (SoapObject) provinceSoapObject1.getProperty(i);
            String Zipcode = soapObject.getProperty("Zipcode").toString();
            String Charg = soapObject.getProperty("Charg").toString();

            Ztwm004 ztwm004 = new Ztwm004();
            ztwm004.setZipcode(Zipcode);
            ztwm004.setCharg(Charg);
            ztwm004.setZcupno(EZcupno);

            list.add(ztwm004);
        }

        return list;
    }

    /**
     * Soap Object解析返回值
     * @param result 返回值
     * @return List
     */
    public static List<Object> parseSoapObject002(SoapObject result) {
        List<Object> list = new ArrayList<>();

        String ECharg = result.getProperty("ECharg").toString();//ERP批次编号
        //String EMessage = result.getProperty("EMessage").toString();//返回信息
        String EType = result.getProperty("EType").toString();//状态
        String EZcupno = result.getProperty("EZcupno").toString();//批次编号
        String EZipcode = result.getProperty("EZipcode").toString();//托盘编码

        list.add(ECharg);
        //list.add(EMessage);
        list.add(EType);
        list.add(EZcupno);
        list.add(EZipcode);

        return list;
    }

    /**
     * 请求ZwmRfcIts001接口物料名称和客户名称
     *
     * @param url        请求URL
     * @param methodName 请求的参数名
     * @return map
     */
    public static List<Object> callWebServiceFor001(String url, final String methodName, HashMap<String, String> properties) {
        //返回的结果集
        List<Object> resultList = new ArrayList<>();
        // set up
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        // SoapObject添加参数
        if (properties != null) {
            for (Iterator<Map.Entry<String, String>> it = properties.entrySet().iterator(); it.hasNext(); ) {
                Map.Entry<String, String> entry = it.next();
                request.addProperty(entry.getKey(), entry.getValue());
            }
        }

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10); // put all required data into a soap
        envelope.dotNet = false;
        envelope.setOutputSoapObject(request);

        HttpTransportSE httpTransport = new HttpTransportSE(url);
        httpTransport.debug = true;
        try {
            httpTransport.call(SOAP_ACTION_001, envelope);
            if (envelope.bodyIn instanceof SoapObject) { // SoapObject = SUCCESS
                SoapObject soapObject = (SoapObject) envelope.bodyIn;
                //解析后的返回list
                resultList = parseSoapObject(soapObject);
                return resultList;
            } else if (envelope.bodyIn instanceof SoapFault) {
                SoapFault soapFault = (SoapFault) envelope.bodyIn;
                try {
                    throw new Exception(soapFault.getMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultList;
    }

    /**
     * Soap Object解析返回值
     * @param result 返回值
     * @return List
     */
    public static List<Object> parseSoapObject(SoapObject result) {
        List<Object> list = new ArrayList<>();
        String EMaktx = result.getProperty("EMaktx").toString();
        String EName1 = result.getProperty("EName1").toString();
        list.add(EMaktx);
        list.add(EName1);

        return list;
    }

    /**
     * 请求ZwmRfcIts004接口获取单位列表
     *
     * @param url        请求URL
     * @param methodName 请求的参数名
     * @return map
     */
    public static Map<String, String> callWebServiceFor004(String url, final String methodName) {
        //返回的结果集
        Map<String, String> map = new HashMap<>();
        // set up
        SoapObject request = new SoapObject(NAMESPACE, methodName);
        // SoapObject添加参数
        Zswm003 zswm003 = new Zswm003();
        request.addProperty("EtZswm003", zswm003);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10); // put all required data into a soap
        envelope.dotNet = false;
        envelope.setOutputSoapObject(request);

        HttpTransportSE httpTransport = new HttpTransportSE(url);
        httpTransport.debug = true;
        try {
            httpTransport.call(SOAP_ACTION_004, envelope);
            if (envelope.bodyIn instanceof SoapObject) {
                SoapObject soapObject = (SoapObject) envelope.bodyIn;
                //解析后的返回list
                map = parseSoapObject004(soapObject);
                return map;
            } else if (envelope.bodyIn instanceof SoapFault) {
                SoapFault soapFault = (SoapFault) envelope.bodyIn;
                try {
                    throw new Exception(soapFault.getMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * Soap Object解析返回值
     *
     * @param result 获取到的值
     * @return map
     */
    public static Map<String, String> parseSoapObject004(SoapObject result) {
        Map<String, String> map = new HashMap<>();
        SoapObject provinceSoapObject1 = (SoapObject) result.getProperty("EtZswm003");
        if (provinceSoapObject1 == null) {
            return null;
        }
        for (int i = 0; i < provinceSoapObject1.getPropertyCount(); i++) {
            SoapObject soapObject = (SoapObject) provinceSoapObject1.getProperty(i);
            String Msehi = soapObject.getProperty("Msehi").toString();
            String Mseh3 = soapObject.getProperty("Mseh3").toString();
            map.put(Msehi, Mseh3);
        }
        return map;
    }

}
