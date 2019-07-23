package com.huqi.qs.elasticsearch.transportclient;

import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsRequestBuilder;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequestBuilder;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import java.io.IOException;

/**
 * @author huqi 20190129
 */
public class PutMapping {
    public static void main(String[] args) {
        Client client = getClient();
        GetResponse response = client.prepareGet("everhomesv3", "generalFormV2Values", "71").execute()
                .actionGet();
        String json = response.getSourceAsString();
        System.out.println(json);

        GetMappingsRequestBuilder getmapping = client.admin().indices().prepareGetMappings();
        MappingMetaData metaData = getmapping.get().getMappings()
                .get("everhomesv3").get("generalFormV2Values");
        try {
            System.out.println(new String(metaData.source().uncompressed()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 即使"generalFormV2Values"这个类型不存在，putMapping不会报错，而是创建这个类型
        PutMappingRequestBuilder putMapping = client.admin().indices().preparePutMapping();
        String mapping = "{\"generalFormV2Values\": {\"properties\": {\"test1903121812\": { \"type\": \"integer\",\"index\": \"not_analyzed\"}}}}";

        putMapping.setIgnoreConflicts(true).setIndices("everhomesv3").setType("generalFormV2Values").setSource(mapping).execute().actionGet();

    }

    static Client getClient() {
        Settings settings = ImmutableSettings.settingsBuilder()
                .put("cluster.name", "elasticsearch65")
                .build();
        TransportClient proxyClient = new TransportClient(settings);
        proxyClient.addTransportAddress(new InetSocketTransportAddress("elasticsearch", 9300));
        return proxyClient;
    }
}
