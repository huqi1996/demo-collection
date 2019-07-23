package com.huqi.qs.elasticsearch.transportclient;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.max.Max;
import org.elasticsearch.search.aggregations.metrics.valuecount.InternalValueCount;

import static jdk.nashorn.internal.objects.Global.Infinity;

/**
 * @author huqi 20190222
 */
public class AggregationQuery {
    public static void main(String[] args) {
        Client client = PutMapping.getClient();
        BoolQueryBuilder query = QueryBuilders.boolQuery();
        // query.must(QueryBuilders.termQuery("formId", 0));
        SearchRequestBuilder builder;
        SearchResponse response;
        InternalValueCount count;
        builder = client.prepareSearch("everhomesv3").setTypes("generalFormV2Values").setQuery(query)
                .addAggregation(AggregationBuilders.count("count").field("id"))
                .setSize(0);
        response = builder.get();
        count = response.getAggregations().get("count");
        System.out.println(count.getValue());
        System.out.println(response);
        System.out.println("-----------------------------------------------------------------------------");
        builder = client.prepareSearch("everhomesv3").setTypes("generalFormV2Values").setQuery(query)
                .addAggregation(AggregationBuilders.max("maxTime").field("createTime"))
                .setSize(0);
        response = builder.get();
        Max max = response.getAggregations().get("maxTime");
        System.out.println(max.getValue() == -Infinity);
        System.out.println(max.getValue() < 0);
        System.out.println(max.getValue());
        System.out.println(response);
        System.out.println("-----------------------------------------------------------------------------");
        builder = client.prepareSearch("everhomesv3").setTypes("generalFormV2Values").setQuery(query)
                .addAggregation(AggregationBuilders.terms("formId").field("formId").size((int) count.getValue()))
                .setSize(0);
        response = builder.get();
        Terms terms = response.getAggregations().get("formId");
        System.out.println(terms.getName());
        System.out.println(response.getHits().getHits().length);
        System.out.println(terms.getBuckets().size());
        for (Terms.Bucket entry : terms.getBuckets()) {
            System.out.println(entry.getKey() + "-(" + entry.getDocCount() + ")");
        }
        System.out.println(response);
    }
}
