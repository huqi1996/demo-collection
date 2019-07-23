package com.huqi.qs.httpclient.service;

import org.apache.http.client.cache.CacheResponseStatus;
import org.apache.http.client.cache.HttpCacheContext;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.cache.CacheConfig;
import org.apache.http.impl.client.cache.CachingHttpClients;

import java.io.IOException;

/**
 * This example demonstrates how to use caching {@link CacheConfig}.
 * <p>
 * HttpClient Cache提供了一个与HTTP / 1.1兼容的缓存层，可以与HttpClient一起使用 - Java相当于浏览器缓存。
 * <p>
 * 这是如何设置基本缓存HttpClient的简单示例。
 * 按照配置，它将存储最多3000个缓存对象，其中每个对象的最大主体大小可能为10240字节。
 * 我们配置CacheConfig并使用这个配置来创建HttpClient。
 * 循环执行一次简单的HTTP GET请求3次，并期望最后两个请求将被缓存。
 * </p>
 *
 * @author huqi 20190110
 */
public class HttpClientCachingExample {

    public static void httpClientCachingExample() {

        CacheConfig cacheConfig = CacheConfig.custom()
                .setMaxCacheEntries(3000)
                .setMaxObjectSize(10240)
                .build();

        CloseableHttpClient cachingClient = CachingHttpClients.custom()
                .setCacheConfig(cacheConfig)
                .build();

        int counter = 3;
        for (int i = 0; i < counter; i++) {
            HttpCacheContext context = HttpCacheContext.create();
            HttpGet httpget = new HttpGet("http://httpbin.org/cache");
            System.out.println("Executing request " + httpget.getRequestLine());
            try (CloseableHttpResponse response = cachingClient.execute(httpget, context)) {
                System.out.println("----------------------------------------");
                CacheResponseStatus responseStatus = context.getCacheResponseStatus();
                switch (responseStatus) {
                    case CACHE_HIT:
                        System.out.println("A response was generated from the cache with " +
                                "no requests sent upstream");
                        break;
                    case CACHE_MODULE_RESPONSE:
                        System.out.println("The response was generated directly by the " +
                                "caching module");
                        break;
                    case CACHE_MISS:
                        System.out.println("The response came from an upstream server");
                        break;
                    case VALIDATED:
                        System.out.println("The response was generated from the cache " +
                                "after validating the entry with the origin server");
                        break;
                    default:
                        return;
                }
                System.out.println("----------------------------------------");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
