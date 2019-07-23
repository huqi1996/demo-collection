package com.huqi.qs.elasticsearch.javalowlevelrestclient;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;

import java.io.IOException;

/**
 * @author huqi 20190129
 */
public class Main {
    public static void main(String[] args) {
        try (RestClient restClient = RestClient.builder(
                new HttpHost("localhost", 9200, "http")).build()) {

            System.out.println(restClient.getNodes().size());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
