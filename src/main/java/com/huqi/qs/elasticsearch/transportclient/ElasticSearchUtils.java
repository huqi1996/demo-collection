package com.huqi.qs.elasticsearch.transportclient;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.WriteConsistencyLevel;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequestBuilder;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.nested.Nested;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.max.Max;

import java.io.IOException;
import java.util.*;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;
import static org.elasticsearch.index.query.QueryBuilders.nestedQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;

/**
 * @author huqi
 * @date 20200409
 */
public class ElasticSearchUtils {
    public static final String CLUSTER_NAME = "elasticsearch65";
    public static final String HOSTNAME = "elasticsearch";
    public static final String INDEX = "everhomesv3";
    public static final String TYPE = "huqiDemo";
    public static final Integer NUMBER = 100;
    public static final Integer LENGTH = 20;

    /**
     * 连接到dev1环境，其中hostname"elasticsearch"是window系统host文件里的配置项
     */
    public static Client getClient() {
        Settings settings = ImmutableSettings.settingsBuilder().put("cluster.name", CLUSTER_NAME).build();
        TransportClient proxyClient = new TransportClient(settings);
        proxyClient.addTransportAddress(new InetSocketTransportAddress(HOSTNAME, 9300));
        return proxyClient;
    }

    /**
     * 查询mapping
     */
    public static void getMapping() {
        MappingMetaData metaData = getClient().admin().indices().prepareGetMappings().get().getMappings().get(INDEX).get(TYPE);
        try {
            System.out.println("getMapping: " + new String(metaData.source().uncompressed()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 修改mapping
     */
    public static void putMapping() {
        getMapping();
        PutMappingRequestBuilder putMapping = getClient().admin().indices().preparePutMapping();
        String mapping = "{\"" + TYPE + "\": {\"properties\": {\"stringField3\": { \"type\": \"string\",\"index\": \"not_analyzed\"}}}}";
        putMapping.setIgnoreConflicts(true).setIndices(INDEX).setType(TYPE).setSource(mapping).execute().actionGet();
        getMapping();
    }

    /**
     * 创建指定数量的文档
     */
    public static void createDocuments(Integer count) {
        Random random = new Random();
        for (int i = 1; i <= count; i++) {
            try {
                int score = random.nextInt(NUMBER);
                XContentBuilder builder = XContentFactory.jsonBuilder().startObject()
                        .field("id", i)
                        .field("score", random.nextInt(NUMBER))
                        .field("level", getLevelByScore(score))
                        .field("name", getRandomString(LENGTH))
                        .field("createTime", System.currentTimeMillis());
                builder.startArray("family");
                for (int j = 0; j <= random.nextInt(5); j++) {
                    builder.startObject()
                            .field("age", random.nextInt(NUMBER))
                            .field("name", getRandomString(LENGTH))
                            .endObject();
                }
                builder.endArray();
                builder.endObject();
                IndexRequestBuilder requestBuilder = getClient().prepareIndex(INDEX, TYPE, String.valueOf(i))
                        .setConsistencyLevel(WriteConsistencyLevel.DEFAULT).setSource(builder).setRefresh(true);
                requestBuilder.execute().actionGet();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        countDocuments(null);
    }

    public static String getLevelByScore(int score) {
        if (score < 60) {
            return "未及格";
        } else if (score < 70) {
            return "及格";
        } else if (score < 80) {
            return "良好";
        } else {
            return "优秀";
        }
    }

    /**
     * 获取随机字符串，纯小写字母
     */
    public static String getRandomString(int count) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < count; i++) {
            builder.append((char) (Math.random() * 26 + 97));
        }
        return builder.toString();
    }

    /**
     * 统计文档数量
     */
    public static void countDocuments(BoolQueryBuilder query) {
        if (query == null) {
            query = QueryBuilders.boolQuery();
        }
        SearchRequestBuilder builder = getClient().prepareSearch(INDEX).setTypes(TYPE).setQuery(query)
                .addAggregation(AggregationBuilders.count("count").field("id")).setSize(0);
        System.out.println("countDocuments: " + getSearchResponse(builder).getAggregations().get("count"));
    }

    /**
     * 执行查询
     */
    public static SearchResponse getSearchResponse(SearchRequestBuilder builder) {
        try {
            System.out.println("------------------------------------------------");
            System.out.println("getSearchResponse: " + builder.toString());
            System.out.println("------------------------------------------------");
            return builder.execute().actionGet();
        } catch (ElasticsearchException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    /**
     * 查询指定文档
     */
    public static void getDocument(String id) {
        GetResponse response = getClient().prepareGet(INDEX, TYPE, id).execute().actionGet();
        System.out.println("getDocument - " + id + ": " + response.getSourceAsString());
    }

    /**
     * 修改指定文档
     */
    public static void updateDocument(String id, Map<String, Object> fields) {
        // 若文档不存在，抛出异常
        // 若文档存在，根据updateRequest的内容修改文档
        try {
            UpdateRequest updateRequest = new UpdateRequest(INDEX, TYPE, id);
            XContentBuilder builder = jsonBuilder().startObject();
            for (Map.Entry<String, Object> entry : fields.entrySet()) {
                // 嵌套类型的field
                if ("family".equals(entry.getKey())) {
                    List<Map<String, Object>> users = (List<Map<String, Object>>) entry.getValue();
                    builder.startArray(entry.getKey());
                    for (Map<String, Object> user : users) {
                        builder.startObject()
                                .field("age", user.get("age"))
                                .field("name", user.get("name"))
                                .endObject();

                    }
                    builder.endArray();
                    continue;
                }
                // mapping里其它field
                if (entry.getValue() == null) {
                    // entry.getValue()不能改为null
                    builder.field(entry.getKey(), entry.getValue());
                } else {
                    try {
                        builder.field(entry.getKey(), Long.valueOf(String.valueOf(entry.getValue())));
                    } catch (NumberFormatException e) {
                        builder.field(entry.getKey(), String.valueOf(entry.getValue()));
                    }
                }
            }
            builder.endObject();
            updateRequest.doc(builder);
            getClient().update(updateRequest).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 随机修改某个文档
     */
    public static void updateDocument() {
        Map<String, Object> fields = new HashMap<>(8);
        Random random = new Random();
        int score = random.nextInt(NUMBER);
        fields.put("score", score);
        fields.put("level", getLevelByScore(score));
        fields.put("name", getRandomString(NUMBER));
        fields.put("updateTime", System.currentTimeMillis());
        List<Map<String, Object>> family = new ArrayList<>();
        for (int i = 0; i <= random.nextInt(5); i++) {
            Map<String, Object> nestedField = new HashMap<>(8);
            nestedField.put("age", random.nextInt(NUMBER));
            nestedField.put("name", getRandomString(NUMBER));
            family.add(nestedField);
        }
        fields.put("family", family);
        String id = String.valueOf(random.nextInt(NUMBER));
        System.out.println("updateDocument - " + id);
        getDocument(id);
        updateDocument(id, fields);
        getDocument(id);
    }

    /**
     * 删除指定文档
     */
    public static void deleteDocument(String id) {
        System.out.println("deleteDocument - " + id);
        countDocuments(null);
        getDocument(id);
        getClient().prepareDelete(INDEX, TYPE, id).setRefresh(true).execute().actionGet();
        countDocuments(null);
        getDocument(id);
    }

    /**
     * 随机删除某个文档
     */
    public static void deleteDocument() {
        Random random = new Random();
        deleteDocument(String.valueOf(random.nextInt(NUMBER)));
    }

    /**
     * 范围查询
     */
    public static void rangeQueryDocument() {
        Random random = new Random();
        int from = random.nextInt(NUMBER);
        int to = random.nextInt(NUMBER);
        BoolQueryBuilder query = QueryBuilders.boolQuery();
        query.must(QueryBuilders.rangeQuery("score").from(from).to(to));
        System.out.println("rangeQueryDocument: " + from + " - " + to);
        countDocuments(query);
    }

    /**
     * 精确匹配
     */
    public static void termQueryDocument() {
        BoolQueryBuilder query = QueryBuilders.boolQuery();
        BoolQueryBuilder childQuery = QueryBuilders.boolQuery();
        childQuery.should(QueryBuilders.termQuery("level", "优秀"));
        childQuery.should(QueryBuilders.termQuery("level", "良好"));
        query.must(childQuery);
        countDocuments(query);
    }

    /**
     * 模糊查询
     */
    public static void wildcardQueryDocument() {
        BoolQueryBuilder query = QueryBuilders.boolQuery();
        query.must(QueryBuilders.wildcardQuery("name", "*" + getRandomString(2) + "*"));
        countDocuments(query);
    }

    /**
     * 嵌套查询
     */
    public static void nestedQueryDocument() {
        BoolQueryBuilder query = QueryBuilders.boolQuery();
        Random random = new Random();
        query.must(nestedQuery("family", termQuery("family.age", random.nextInt(NUMBER))));
        countDocuments(query);
    }

    /**
     * 嵌套聚合
     */
    public static void nestedAggregationDocument() {
        BoolQueryBuilder query = QueryBuilders.boolQuery();
        SearchRequestBuilder builder = getClient().prepareSearch(INDEX).setTypes(TYPE).setQuery(query)
                .addAggregation(AggregationBuilders.nested("nested").path("family")
                        .subAggregation(AggregationBuilders.max("maxAge").field("family.age")))
                .setSize(0);
        SearchResponse response = getSearchResponse(builder);
        Nested nested = response.getAggregations().get("nested");
        Max max = nested.getAggregations().get("maxAge");
        System.out.println("nestedAggregationDocument: max family.age is " + (long) max.getValue());

        builder = getClient().prepareSearch(INDEX).setTypes(TYPE).setQuery(query)
                .addAggregation(AggregationBuilders.terms("level").field("level").size(Integer.MAX_VALUE)).setSize(0);
        response = getSearchResponse(builder);
        Terms terms = response.getAggregations().get("level");
        Map<String, Long> levelGroup = new HashMap<>(8);
        for (Terms.Bucket entry : terms.getBuckets()) {
            levelGroup.put(entry.getKey(), entry.getDocCount());
        }
        System.out.println("nestedAggregationDocument: group by level : " + levelGroup.toString());
    }

    /**
     * 查询文档
     */
    public static void queryDocument() {
        rangeQueryDocument();
        termQueryDocument();
        wildcardQueryDocument();
        nestedQueryDocument();
        nestedAggregationDocument();
    }

    public static void main(String[] args) {/*
        putMapping();
        createDocuments(NUMBER);
        updateDocument();
        deleteDocument();
        queryDocument();*/
        System.out.println(getClient().prepareGet(INDEX, TYPE, "400").setRefresh(true).execute().actionGet().getSource() == null);
    }
}
