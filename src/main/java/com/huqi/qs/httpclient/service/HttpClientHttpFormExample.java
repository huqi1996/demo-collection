package com.huqi.qs.httpclient.service;

import com.huqi.qs.httpclient.util.HttpClientUtil;
import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This example demonstrates the use of {@link HttpPost} request method.
 * And sending HTML Form request parameters
 * <p>
 * 许多应用程序需要模拟提交HTML表单的过程，以便登录到Web应用程序或提交输入数据。
 * HttpClient提供实体类UrlEncodedFormEntity来促进该过程。
 * <p>
 * 在以下示例中，我们将HTML表单参数发布到资源: http://httpbin.org/post 。
 * 该资源确认数据并返回一个JSON对象，只需将其打印到控制台。
 * 发送HTML表单参数时，通常应将内容类型设置为application/x-www-form-urlencoded，
 * 但Apache HttpClient会自动检测内容类型并相应地设置它。
 * </p>
 *
 * @author huqi 20190110
 */
public class HttpClientHttpFormExample {

    public static void httpClientHttpFormExample() {

        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {

            List<NameValuePair> form = new ArrayList<>();
            form.add(new BasicNameValuePair("foo", "bar"));
            form.add(new BasicNameValuePair("employee", "Jack"));
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(form, Consts.UTF_8);

            HttpPost httpPost = new HttpPost("http://httpbin.org/post");
            httpPost.setEntity(entity);
            HttpClientUtil.printResponseBody(httpclient, httpPost);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

