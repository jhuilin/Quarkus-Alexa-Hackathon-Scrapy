package com.example.Service;

import com.example.Utils.Utils;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class DataService {

    @Autowired
    RestHighLevelClient restHighLevelClient;

    public List<Map<String, Object>> getBeef() throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < Utils.BeefCount; ++i) {
            list.add(getList(Utils.BEEF, "" + (i + 1)));
        }
        System.out.println("search from beef");
        return list;
    }

    public List<Map<String, Object>> getMilk() throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < Utils.MilkCount; ++i) {
            list.add(getList(Utils.MILK, "" + (i + 1)));
        }
        System.out.println("search from milk");
        return list;
    }

    private Map<String, Object> getList(String index, String id) throws IOException {
        GetRequest getRequest = new GetRequest(index, id);
        GetResponse getResponse = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
        return getResponse.getSourceAsMap();
    }
}
