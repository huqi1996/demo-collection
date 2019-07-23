package com.huqi.qs.httpclient.service;

import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import javax.net.ssl.SSLException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.UnknownHostException;

/**
 * This example demonstrates the use of {@link HttpRequestRetryHandler}.
 * <p>
 * 在以下示例中，我们演示了如何创建自定义HttpRequestRetryHandler以启用自定义异常恢复机制。
 * 当使用这个接口时，需要实现retryRequest方法。
 * 这使我们能够定义一个自定义的重试计数机制和异常恢复机制。
 * <p>
 * 我们实现HttpRequestRetryHandler接口来实现自定义重试和异常恢复机制。
 * 这使我们能够跟踪发送请求的次数，然后再将其处理为相应的异常机制。
 * </p>
 *
 * @author huqi 20190110
 */
public class HttpClientRetryHandlerExample {

    public static void httpClientRetryHandlerExample() {

        try (CloseableHttpClient httpclient = HttpClients.custom()
                .setRetryHandler(retryHandler())
                .build()) {
            HttpGet httpget = new HttpGet("http://localhost:1234");
            System.out.println("Executing request " + httpget.getRequestLine());
            httpclient.execute(httpget);
            System.out.println("----------------------------------------");
            System.out.println("Request finished");
            System.out.println("----------------------------------------");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static HttpRequestRetryHandler retryHandler() {
        return (exception, executionCount, context) -> {

            System.out.println("try request: " + executionCount);

            if (executionCount >= 5) {
                // Do not retry if over max retry count
                return false;
            }
            if (exception instanceof InterruptedIOException) {
                // Timeout
                return false;
            }
            if (exception instanceof UnknownHostException) {
                // Unknown host
                return false;
            }
            if (exception instanceof SSLException) {
                // SSL handshake exception
                return false;
            }
            HttpClientContext clientContext = HttpClientContext.adapt(context);
            HttpRequest request = clientContext.getRequest();
            boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);
            if (idempotent) {
                // Retry if the request is considered idempotent
                return true;
            }
            return false;
        };
    }
}
