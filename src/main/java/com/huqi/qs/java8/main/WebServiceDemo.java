package com.huqi.qs.java8.main;

import webservice.OfsTodoDataWebService;
import webservice.OfsTodoDataWebServicePortType;

import java.sql.Timestamp;

public class WebServiceDemo {
    public static void main(String[] args) {
        try {

            OfsTodoDataWebServicePortType t = new OfsTodoDataWebService().getOfsTodoDataWebServiceHttpPort();
            try {
//

                String json = "{\"syscode\":\"mh\"," +
                        "\"flowid\":\"demo001\"," +
                        "\"userid\":\"zhd\"}";
                System.out.println(json);
                String ret = t.deleteRequestInfoByJson(json);
                System.out.println(ret);
                json = "{\"syscode\":\"mh\"," +
                        "\"flowid\":\"demo001\"," +
                        "\"requestname\":\"测试001\"," +
                        "\"workflowname\":\"门户网站消息\"," +
                        "\"nodename\":\"申请人创建\"," +
                        "\"pcurl\":\"/showtask.aspx?id=A00000000008\"," +
                        "\"appurl\":\"/showtask.aspx?id=A00000000008\"," +
                        "\"isremark\":\"0\"," +
                        "\"viewtype\":\"0\"," +
                        "\"creator\":\"zhd\"," +
                        "\"createdatetime\":\"2018-11-07 10:10:10\"," +
                        "\"receiver\":\"zhd\"," +
                        "\"receivedatetime\":\"2018-11-08 08:10:10\"}";
                System.out.println(json);
                ret = t.receiveRequestInfoByJson(json);
                System.out.println(ret);
                json = "{\"syscode\":\"mh\"," +
                        "\"flowid\":\"demo001\"," +
                        "\"userid\":\"zhd\"}";
                System.out.println(json);
                ret = t.deleteUserRequestInfoByJson(json);
                System.out.println(ret);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(new Timestamp(1572601711000L).toString());
    }
}
