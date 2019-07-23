package com.huqi.qs.httpclient.service;


import com.huqi.qs.httpclient.util.HttpClientConstants;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.DateUtils;
import org.apache.http.conn.ManagedHttpClientConnection;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpCoreContext;

import javax.net.ssl.SSLSession;
import java.io.IOException;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;

/**
 * This example demonstrates how to obtain server certificates {@link X509Certificate}.
 * <p>
 * 以下教程演示了如何使用Apache HttpClient 4.5从资源服务器获取证书。
 * 证书用于通过使用SSL / TLS的HTTPS保护客户端和服务器之间的连接。
 * 当您需要有关证书的详细信息时，例如:证书何时到期？谁颁发证书？等等。或者在某些情况下需要读取服务器证书。
 * <p>
 * 在以下示例中，我们向https://www.baidu.com发出请求以获取服务器证书。
 * 首先，我们创建一个HttpResponseInterceptor，它将从SSLSession中读取证书(如果存在)，
 * 并将证书添加到HttpContext中，以便稍后用于处理。
 * 接下来，创建一个自定义的HttpClient并使用addInterceptorLast()工厂方法添加拦截器。
 * 最后，向资源服务器发出一个HttpGet请求，并从HttpContext获取服务器证书，我们之前将它们放在这里。
 * 现在拥有了证书，然后遍历集合并将一些数据打印到控制台。
 * </p>
 *
 * @author huqi 20190110
 */
public class HttpClientGetServerCertificate {

    public static void httpClientGetServerCertificate() {

        // create http response certificate interceptor
        HttpResponseInterceptor certificateInterceptor = (httpResponse, context) -> {
            ManagedHttpClientConnection routedConnection = (ManagedHttpClientConnection) context.getAttribute(HttpCoreContext.HTTP_CONNECTION);
            SSLSession sslSession = routedConnection.getSSLSession();
            if (sslSession != null) {

                // get the server certificates from the {@Link SSLSession}
                Certificate[] certificates = sslSession.getPeerCertificates();

                // add the certificates to the context, where we can later grab it from
                context.setAttribute(HttpClientConstants.PEER_CERTIFICATES, certificates);
            }
        };
        try (
                // create closable http client and assign the certificate interceptor
                CloseableHttpClient httpClient = HttpClients.custom().addInterceptorLast(certificateInterceptor).build()) {

            // make HTTP GET request to resource server
            HttpGet httpget = new HttpGet("https://www.baidu.com");
            System.out.println("Executing request " + httpget.getRequestLine());

            // create http context where the certificate will be added
            HttpContext context = new BasicHttpContext();
            httpClient.execute(httpget, context);

            // obtain the server certificates from the context
            Certificate[] peerCertificates = (Certificate[]) context.getAttribute(HttpClientConstants.PEER_CERTIFICATES);

            // loop over certificates and print meta-data
            for (Certificate certificate : peerCertificates) {
                X509Certificate real = (X509Certificate) certificate;
                System.out.println("----------------------------------------");
                System.out.println("Type: " + real.getType());
                System.out.println("Signing Algorithm: " + real.getSigAlgName());
                System.out.println("IssuerDN Principal: " + real.getIssuerX500Principal());
                System.out.println("SubjectDN Principal: " + real.getSubjectX500Principal());
                System.out.println("Not After: " + DateUtils.formatDate(real.getNotAfter(), "dd-MM-yyyy"));
                System.out.println("Not Before: " + DateUtils.formatDate(real.getNotBefore(), "dd-MM-yyyy"));
                System.out.println("----------------------------------------");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

