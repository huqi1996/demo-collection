package com.huqi.qs.httpclient.service;

import com.huqi.qs.httpclient.util.HttpClientUtil;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 * HTTP PUT请求方法请求服务器接受并存储提供的URI中包含的实体。
 * 如果该URI引用已经存在的资源，则该资源被修改; 如果URI不指向现有资源，则服务器可以使用该URI创建资源。
 *
 * @author huqi
 */
public class HttpPutRequestMethodExample {

    public static void httpPutRequestMethodExample() {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpPut httpPut = new HttpPut("http://httpbin.org/put");
            httpPut.setEntity(new StringEntity("Hello, World"));
            HttpClientUtil.printResponseBody(httpclient, httpPut);

//            HttpPut httpPut = new HttpPut("http://10.1.120.65:9200/everhomesv3/generalFormV2Values/_mapping?pretty");
//            //设置header
//            httpPut.setHeader("Content-type", "application/json");
//            //组织请求参数
//            String json = "{\"generalFormV2Values\": {\"properties\": {\"测试4\": { \"type\": \"string\",\"index\": \"not_analyzed\"}}}}";
//            StringEntity stringEntity = new StringEntity(json, "UTF-8");
//            httpPut.setEntity(stringEntity);
//            HttpClientUtils.printResponseBody(httpclient, httpPut);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
