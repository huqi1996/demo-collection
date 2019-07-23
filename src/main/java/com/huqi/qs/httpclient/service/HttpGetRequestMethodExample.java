package com.huqi.qs.httpclient.service;

import com.huqi.qs.httpclient.util.HttpClientUtil;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;

/**
 * Http GET方法表示指定资源的表示形式。
 * 这可能与获取HTML页面一样简单，或者使用JSON，XML等格式获取资源。
 * 使用HTTP GET请求方法的请求应该是幂等的，这意味着:这些应该只能检索数据并且应该没有其他效果.
 *
 * @author huqi
 */
public class HttpGetRequestMethodExample {

    public static void httpGetRequestMethodExample() {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpGet httpget = new HttpGet("http://httpbin.org/get");
            HttpClientUtil.printResponseBody(httpclient, httpget);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}