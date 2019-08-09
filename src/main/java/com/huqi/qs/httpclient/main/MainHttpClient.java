package com.huqi.qs.httpclient.main;

import com.huqi.qs.httpclient.service.*;

/**
 * @author huqi 20190110
 */
public class MainHttpClient {
    public static void main(String[] args) {
        // HTTP GET请求
        HttpGetRequestMethodExample.httpGetRequestMethodExample();
        // HTTP POST请求
        HttpPostRequestMethodExample.httpPostRequestMethodExample();
        // HTTP PUT请求
        HttpPutRequestMethodExample.httpPutRequestMethodExample();
        // HTTP DELETE请求
        HttpDeleteRequestMethodExample.httpDeleteRequestMethodExample();
        // 使用Apache HttpClient 4.5从资源服务器获取证书
        HttpClientGetServerCertificate.httpClientGetServerCertificate();
        // 允许不可信的自签名证书，关闭主机名验证
        HttpClientAcceptSelfSignedCertificate.httpClientAcceptSelfSignedCertificate();
        // 自定义的重试计数机制和异常恢复机制
        HttpClientRetryHandlerExample.httpClientRetryHandlerExample();
        // 设置基本缓存HttpClient
        HttpClientCachingExample.httpClientCachingExample();
        // 模拟提交HTML表单
        HttpClientHttpFormExample.httpClientHttpFormExample();
        // 重定向
        HttpClientRedirectHandlingExample.httpClientRedirectHandlingExample();
        // HttpClient允许添加，编辑，删除或枚举http头
        HttpClientCustomHeadersExample.httpClientCustomHeadersExample();
        // 分段上传文件
        HttpClientMultipartUploadExample.httpClientMultipartUploadExample();
    }
}

