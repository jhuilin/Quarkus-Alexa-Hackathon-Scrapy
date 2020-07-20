package org.acme.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.acme.Models.Product;
import org.acme.Utils.Utils;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.util.List;

@Singleton
public class BuildData {

    @Inject
    private RestHighLevelClient restHighLevelClient;

    private ObjectMapper objectMapper = new ObjectMapper();

    @PostConstruct
    public void fetch() throws Exception {
        fetchMilk();
        fetchBeef();
        fetchCookies();
        fetchJuice();
        System.out.println("post construct");
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

    public void fetchJuice() throws Exception {
        List<Product> juices = Utils.parse("juice");
        Utils.JuiceCount = juices.size();
        if (!testExistIndex(Utils.JUICE)) {
            createIndex(Utils.JUICE);
            buildData(Utils.JUICE, juices);
            System.out.println("juice create");
        } else {
            updateData(Utils.JUICE, juices);
            System.out.println("juice update");
        }
    }

    public void fetchCookies() throws Exception {
        List<Product> cookies = Utils.parse("cookies");
        Utils.CookiesCount = cookies.size();
        if (!testExistIndex(Utils.COOKIES)) {
            createIndex(Utils.COOKIES);
            buildData(Utils.COOKIES, cookies);
            System.out.println("juice create");
        } else {
            updateData(Utils.COOKIES, cookies);
            System.out.println("juice update");
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
