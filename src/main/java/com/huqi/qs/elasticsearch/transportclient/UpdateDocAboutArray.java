package com.huqi.qs.elasticsearch.transportclient;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;

import java.util.Map;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;
import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * @author huqi
 * @date 20190327
 */
public class UpdateDocAboutArray {
    public static void main(String[] args) throws Exception {

        Client client = ElasticSearchUtils.getClient();
        IndexRequest indexRequest = new IndexRequest("test190307", "generalFormV2Values", "666")
                .source(jsonBuilder()
                        .startObject()
                        .startArray("userId")
                        .value(111).value(222)
                        .endArray()
                        .endObject());
        UpdateRequest updateRequest = new UpdateRequest("test190307", "generalFormV2Values", "666")
                .doc(jsonBuilder()
                        .startObject()
                        .startArray("userName")
                        .value("ccc").value("ddd")
                        .endArray()
                        .endObject())
                .upsert(indexRequest);
        client.update(updateRequest).get();

        BoolQueryBuilder query = QueryBuilders.boolQuery();
        query.must(termQuery("userName", "ccc"));
        SearchResponse response = client.prepareSearch("test190307").setTypes("generalFormV2Values")
                .setSearchType(SearchType.QUERY_THEN_FETCH).setQuery(query).setSize(100).execute()
                .actionGet();
        System.out.println(response);
        SearchHit[] responseHits = response.getHits().getHits();
        for (SearchHit hit : responseHits) {
            Map<String, Object> resultMap = hit.getSource();
            System.out.println(resultMap);
        }

        System.out.println("------------------------------------------------------------------------------------------");

        IndexRequest indexRequest2 = new IndexRequest("test190307", "generalFormV2Values", "667")
                .source(jsonBuilder()
                        .startObject()
                        .startArray("usersId")
                        .startObject().field("userId", "111").field("completeTime", 1).endObject()
                        .startObject().field("userId", "111").field("completeTime", 2).endObject()
                        .startObject().field("userId", "111").field("completeTime", 3).endObject()
                        .startObject().field("userId", "222").field("completeTime", 4).endObject()
                        .startObject().field("userId", "222").field("completeTime", 5).endObject()
                        .startObject().field("userId", "222").field("completeTime", 6).endObject()
                        .endArray()
                        .endObject());

        XContentBuilder builder = jsonBuilder()
                .startObject()
                .startArray("usersId")
                .startObject().field("userId", "111").field("completeTime", 1).endObject()
                .startObject().field("userId", "111").field("completeTime", 2).endObject()
                .startObject().field("userId", "111").field("completeTime", 3).endObject()
                .startObject().field("userId", "222").field("completeTime", 4).field("flowNodeId", 1).endObject()
                .startObject().field("userId", "222").field("completeTime", 5).field("flowNodeId", 2).endObject()
                .startObject().field("userId", "222").field("completeTime", 6).field("flowNodeId", 3).endObject()
                .endArray()
                .endObject();

        UpdateRequest updateRequest2 = new UpdateRequest("test190307", "generalFormV2Values", "667")
                .doc(builder)
                .upsert(indexRequest2);
        client.update(updateRequest2).get();

        BoolQueryBuilder query2 = QueryBuilders.boolQuery();
        query2.must(termQuery("usersId.completeTime", 6));
        SearchResponse response2 = client.prepareSearch("test190307").setTypes("generalFormV2Values")
                .setQuery(query2).setSize(100).execute()
                .actionGet();
        System.out.println(response2);
        responseHits = response2.getHits().getHits();
        for (SearchHit hit : responseHits) {
            Map<String, Object> resultMap = hit.getSource();
            System.out.println(resultMap);
            System.out.println(resultMap.get("usersId"));
            String usersId = String.valueOf(resultMap.get("usersId")).replace(" ", "");
            System.out.println(usersId.substring(1, usersId.length() - 2));
            String[] arr = usersId.substring(1, usersId.length() - 2).split("},");
            for (String userId : arr) {
                if (userId.contains("completeTime=5")) {
                    String[] properties = userId.substring(1).split(",");
                    for (String property : properties) {
                        if (property.contains("completeTime")) {
                            System.out.println(property.substring(property.indexOf("=") + 1));
                        }
                    }
                }
            }
        }
    }
}
