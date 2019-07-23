package com.huqi.qs.httpclient.util;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * @author huqi
 */
public class HttpClientUtil {

    public static void printResponseBody(CloseableHttpClient httpclient, HttpUriRequest httpUriRequest) {
        System.out.println("Executing request " + httpUriRequest.getRequestLine());
        // Create a custom response handler
        ResponseHandler<String> responseHandler = response -> {
            int status = response.getStatusLine().getStatusCode();
            if (status >= HttpClientConstants.SUCCESS_CODE && status < HttpClientConstants.REDIRECT_CODE) {
                HttpEntity entity = response.getEntity();
                return entity != null ? EntityUtils.toString(entity) : null;
            } else {
                throw new ClientProtocolException("Unexpected response status: " + status);
            }
        };
        try {
            String responseBody = httpclient.execute(httpUriRequest, responseHandler);
            System.out.println("----------------------------------------");
            System.out.println(responseBody);
            System.out.println("----------------------------------------");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}