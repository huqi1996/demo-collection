package com.huqi.qs.httpclient.service;

import com.huqi.qs.httpclient.util.HttpClientUtil;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.File;
import java.io.IOException;

/**
 * This example demonstrates the use of {@link HttpPost} request method.
 * And sending Multipart Form requests
 * <p>
 * 在这个例子中，我们将演示如何使用HttpClient 4.5来分段上传文件。
 * 我们使用MultipartEntityBuilder创建一个HttpEntity。
 * 当创建构建器时，添加一个二进制体 - 包含将要上传的文件以及一个文本正文。
 * 接下来，使用RequestBuilder创建一个HTTP请求，并分配先前创建的HttpEntity。
 * </p>
 *
 * @author huqi 20190110
 */
public class HttpClientMultipartUploadExample {

    public static void httpClientMultipartUploadExample() {

        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {

            File file = new File(HttpClientMultipartUploadExample.class.getResource("/java-duke.png").getFile());
            String message = "This is a multipart post";

            // build multipart upload request
            HttpEntity data = MultipartEntityBuilder.create()
                    .setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
                    .addBinaryBody("upFile", file, ContentType.DEFAULT_BINARY, file.getName())
                    .addTextBody("text", message, ContentType.DEFAULT_BINARY)
                    .build();

            // build http request and assign multipart upload data
            HttpUriRequest request = RequestBuilder
                    .post("http://httpbin.org/post")
                    .setEntity(data)
                    .build();

            HttpClientUtil.printResponseBody(httpclient, request);
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
    }
}

