package com.huqi.qs.httpclient.service;

import org.apache.http.HttpHost;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * This example demonstrates the use of {@link HttpGet} request method.
 * and handling redirect strategy with {@link LaxRedirectStrategy}
 * <p>
 * HttpClient自动处理所有类型的重定向，除了HTTP规范明确禁止的那些重定向需要用户干预。
 * 请参阅其他(状态码303)在POST上重定向，并且按照HTTP规范的要求将PUT请求转换为GET请求。
 * 可以使用自定义重定向策略来放宽由HTTP规范施加的对POST方法的自动重定向的限制。
 * <p>
 * 在下面的例子中，我们对资源 http://httpbin.org/redirect/3 进行 HTTP GET。
 * 此资源重新导向到另一个资源x次。
 * 可以使用URIUtils.resolve(httpGet.getURI(), target, redirectLocations)来获取最终的Http位置。
 * </p>
 *
 * @author huqi 20190110
 */
public class HttpClientRedirectHandlingExample {

    public static void httpClientRedirectHandlingExample() {

        try (CloseableHttpClient httpclient = HttpClients.custom()
                .setRedirectStrategy(new LaxRedirectStrategy())
                .build()) {
            HttpClientContext context = HttpClientContext.create();
            HttpGet httpGet = new HttpGet("http://httpbin.org/redirect/3");
            System.out.println("Executing request " + httpGet.getRequestLine());
            System.out.println("----------------------------------------");

            httpclient.execute(httpGet, context);
            HttpHost target = context.getTargetHost();
            List<URI> redirectLocations = context.getRedirectLocations();
            URI location = URIUtils.resolve(httpGet.getURI(), target, redirectLocations);
            System.out.println("Final HTTP location: " + location.toASCIIString());
            System.out.println("----------------------------------------");

        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
