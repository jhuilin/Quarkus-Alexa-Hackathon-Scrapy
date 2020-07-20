package org.acme.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.scheduler.Scheduled;
import jdk.jshell.execution.Util;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static final Logger log = LoggerFactory.getLogger(BuildData.class);

    @Scheduled(cron="0 15 10 * * ?")
    public void fetch() throws Exception {
        for (String key : Utils.KEYWORDS){
            String keyWord = key.toLowerCase();
            List<Product> list = Utils.parse(keyWord);
            Utils.COUNT.put(keyWord, list.size());
            if (!testExistIndex(keyWord)){
                createIndex(keyWord);
                buildData(keyWord, list);
                log.error(keyWord + " created");
                System.out.println(keyWord + " created");
            } else {
                updateData(keyWord, list);
                log.error(keyWord + " updated");
                System.out.println(keyWord + " updated");
            }
        }
        log.error("data stores in ELS");
        log.error("auto scheduler");
        System.out.println("auto scheduler");

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
