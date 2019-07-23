package com.huqi.qs.httpclient.service;

import com.huqi.qs.httpclient.util.HttpClientUtil;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 * @author huqi 20190110
 */
public class HttpDeleteRequestMethodExample {

    public static void httpDeleteRequestMethodExample() {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpDelete httpDelete = new HttpDelete("http://httpbin.org/delete");
            HttpClientUtil.printResponseBody(httpclient, httpDelete);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}