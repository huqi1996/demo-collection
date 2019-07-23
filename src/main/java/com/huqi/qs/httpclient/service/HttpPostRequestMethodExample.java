package com.huqi.qs.httpclient.service;

import com.huqi.qs.httpclient.util.HttpClientUtil;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 * HTTP POST请求方法请求服务器接受请求中包含的实体作为URI标识的Web资源。
 * 发布的数据可以是但不限于现有资源的注释或数据格式化的JSON，XML或提交的表单数据。
 * 服务器可以使用发布的数据来更新数据库中的资源，或处理这些数据。
 *
 * @author huqi
 */
public class HttpPostRequestMethodExample {
    public static void main(String[] args) {
        httpPostRequestMethodExample();
    }

    public static void httpPostRequestMethodExample() {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost("http://httpbin.org/post");
            httpPost.setEntity(new StringEntity("Hello, World"));
            HttpClientUtil.printResponseBody(httpclient, httpPost);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}