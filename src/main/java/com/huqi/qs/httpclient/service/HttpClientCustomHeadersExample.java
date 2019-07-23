package com.huqi.qs.httpclient.service;

import com.huqi.qs.httpclient.util.HttpClientUtil;
import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * This example demonstrates how to use custom http headers.
 * <p>
 * HTTP消息可以包含许多描述消息属性的标头，例如内容长度，内容类型，授权等。
 * HttpClient提供了检索，添加，删除和枚举标头的方法。
 * <p>
 * HttpClient允许我们添加，编辑，删除或枚举http头。
 * 首先来看看在HttpClient上设置默认标头。
 * 接下来，我们在消息上添加自定义HTTP请求标头。
 * </p>
 *
 * @author huqi 20190110
 */
public class HttpClientCustomHeadersExample {

    public static void httpClientCustomHeadersExample() {

        // create custom http headers for httpclient
        List<Header> defaultHeaders =
//              Arrays.asList(new BasicHeader("X-Default-Header", "default header httpclient"))
                Collections.singletonList(new BasicHeader("X-Default-Header", "default header httpclient"));
        try (// setting custom http headers on the httpclient
             CloseableHttpClient httpclient = HttpClients
                     .custom()
                     .setDefaultHeaders(defaultHeaders)
                     .build()) {

            // setting custom http headers on the http request
            HttpUriRequest request = RequestBuilder.get()
                    .setUri("http://httpbin.org/headers")
                    .setHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                    .setHeader(HttpHeaders.FROM, "https://memorynotfound.com")
                    .setHeader("X-Custom-Header", "custom header http request")
                    .build();

            HttpClientUtil.printResponseBody(httpclient, request);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
