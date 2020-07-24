package com.huqi.qs.elasticsearch.transportclient;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.Client;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * @author huqi
 * @date 20190228
 */
public class UpdateDoc {
    public static void main(String[] args) throws Exception {
        Client client = ElasticSearchUtils.getClient();
        UpdateRequest updateRequest;

        // 添加field
        // 若文档不存在，抛出异常
        // 若文档存在，根据updateRequest的内容修改文档
//        updateRequest = new UpdateRequest("everhomesv3", "generalFormV2Values", "161")
//                .doc(jsonBuilder().startObject()
//                        .field("test190228", "test1902288888")
//                        .endObject());
//        client.update(updateRequest).get();


        // 修改field的value
        // 若文档不存在，根据indexRequest的内容新建文档
        // 若文档存在，根据updateRequest的内容修改文档
        IndexRequest indexRequest = new IndexRequest("test190307", "generalFormV2Values", "4")
                .source(jsonBuilder()
                        .startObject()
                        .field("test111", 111111)
                        .field("test222", 222222)
                        .field("id", 4)
                        .endObject());
        updateRequest = new UpdateRequest("test190307", "generalFormV2Values", "4")
                .doc(jsonBuilder().startObject()
                        .field("test111", 1111333)
                        .field("test111", 333333)
                        .field("id", 4)
                        .endObject())
                .upsert(indexRequest);
        client.update(updateRequest).get();


//        client.prepareUpdate("everhomesv3", "generalFormV2Values", "79")
//                .setScript("ctx._source.remove(\"test190228\")", ScriptService.ScriptType.INLINE).get();

//        updateRequest = new UpdateRequest("everhomesv3", "generalFormV2Values", "79")
//                .script("ctx._source.remove(\"test190228\")");
//        client.update(updateRequest).get();

//        updateRequest = new UpdateRequest("everhomesv3", "generalFormV2Values", "79")
//                .script("ctx._source.test190228 = \"111\"");
//        client.update(updateRequest).get();
    }
}
