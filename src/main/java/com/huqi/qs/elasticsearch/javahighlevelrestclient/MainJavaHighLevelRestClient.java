package com.huqi.qs.elasticsearch.javahighlevelrestclient;

import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author huqi
 */
public class MainJavaHighLevelRestClient {
    public static void main(String[] args) {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http")).build());
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("user", "huqi");
        jsonMap.put("postDate", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        jsonMap.put("message", "trying out Elasticsearch");
        IndexRequest indexRequest = new IndexRequest("index", "type", "1")
                .source(jsonMap);
        try {
            IndexResponse indexResponse = client.index(indexRequest);
            System.out.println(indexResponse.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }
}
