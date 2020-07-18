package com.example.Service;

import com.example.Model.Product;
import com.example.Utils.Utils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

@Service
public class BuildData {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    private ObjectMapper objectMapper = new ObjectMapper();

    @PostConstruct
    public void fetch() throws Exception {
        fetchMilk();
        fetchBeef();
    }

    public void fetchMilk() throws Exception {
        List<Product> milks = Utils.parse("milk");
        Utils.MilkCount = milks.size();
        if (!testExistIndex(Utils.MILK)) {
            createIndex(Utils.MILK);
            buildData(Utils.MILK, milks);
            System.out.println("milk create");
        } else {
            updateData(Utils.MILK, milks);
            System.out.println("milk update");
        }
    }

    public void fetchBeef() throws Exception {
        List<Product> beefs = Utils.parse("beef");
        Utils.BeefCount = beefs.size();
        if (!testExistIndex(Utils.BEEF)) {
            createIndex(Utils.BEEF);
            buildData(Utils.BEEF, beefs);
            System.out.println("beef create");
        } else {
            updateData(Utils.BEEF, beefs);
            System.out.println("beef update");
        }
    }

    private boolean testExistIndex(String name) throws IOException {
        GetIndexRequest request = new GetIndexRequest(name);
        boolean isExit = restHighLevelClient.indices().exists(request, RequestOptions.DEFAULT);
        return isExit;
    }

    private void createIndex(String name) throws IOException {
        CreateIndexRequest request = new CreateIndexRequest(name);
        restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);
    }

    private <E> void updateData(String index, List<E> object) throws IOException {
        BulkRequest bulkRequest = new BulkRequest();
        for (int i = 0; i < object.size(); ++i) {
            bulkRequest.add(
                    new UpdateRequest(index, "" + (i + 1))
                            .doc(objectMapper.writeValueAsString(object.get(i)), XContentType.JSON)
            );
        }
        restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
    }

    private <E> void buildData(String index, List<E> object) throws IOException {
        BulkRequest bulkRequest = new BulkRequest();
        for (int i = 0; i < object.size(); ++i) {
            bulkRequest.add(
                    new IndexRequest(index)
                            .id("" + (i + 1))
                            .source(objectMapper.writeValueAsString(object.get(i)), XContentType.JSON)
            );
        }
        restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
    }




}
