package org.acme.Service;

import org.acme.Utils.Utils;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Singleton
public class DataService {

    @Inject
    private RestHighLevelClient restHighLevelClient;

    @Inject
    private BuildData buildData;

    public static boolean isExecute = false;

    private static final Logger log = LoggerFactory.getLogger(DataService.class);

    public List<Map<String, Object>> getData(String key) throws Exception {
        prepareData();
        List<Map<String, Object>> list = new ArrayList<>();
        String keyWord = key.toLowerCase();
        if (!Utils.COUNT.containsKey(keyWord))
            return new LinkedList<>();
        int size = Utils.COUNT.get(keyWord);
        for (int i = 0; i < size; ++i){
            Map<String, Object> var = getList(keyWord, "" + (i + 1));
            if (var != null){
                list.add(var);
            } else {
                break;
            }
        }
        System.out.println("search from "+ keyWord);
        log.error("search from "+ keyWord);
        return list;
    }

    private Map<String, Object> getList(String index, String id) throws IOException {
        GetRequest getRequest = new GetRequest(index, id);
        GetResponse getResponse = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
        if (getResponse.isExists()){
            return getResponse.getSourceAsMap();
        }
        return null;
    }

    private void prepareData() throws Exception {
        if (isExecute == false){
            buildData.fetch();
            isExecute = true;
        }
    }
}
