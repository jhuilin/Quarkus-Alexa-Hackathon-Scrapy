package org.acme.Service;

import org.acme.Utils.Utils;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Singleton
public class DataService {

    @Inject
    private RestHighLevelClient restHighLevelClient;

//    public List<Map<String, Object>> getBeef() throws IOException {
//        List<Map<String, Object>> list = new ArrayList<>();
//        for (int i = 0; i <= Utils.BeefCount + 10; ++i) {
//            if (getList(Utils.BEEF, "" + (i + 1)) != null)
//                list.add(getList(Utils.BEEF, "" + (i + 1)));
//            else
//                break;
//        }
////        for (Map<String, Object> stringObjectMap : list) {
////            System.out.println(stringObjectMap.toString());
////        }
//        System.out.println("search from beef!");
//        return list;
//    }
    public List<Map<String, Object>> getBeef() throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        int index = 0;
        while (true){
            Map<String, Object> var = getList(Utils.BEEF, "" + (++index));
            if (var != null){
                list.add(var);
            } else {
                break;
            }
        }
        for (Map<String, Object> stringObjectMap : list) {
            System.out.println(stringObjectMap.toString());
        }
        System.out.println("search from beef!");
        return list;
    }

    public List<Map<String, Object>> getMilk() throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        int index = 0;
        while (true){
            Map<String, Object> var = getList(Utils.MILK, "" + (++index ));
            if (var != null){
                list.add(var);
            } else {
                break;
            }
        }
        for (Map<String, Object> stringObjectMap : list) {
            System.out.println(stringObjectMap.toString());
        }
        System.out.println("search from milk!");
        return list;
    }

    public List<Map<String, Object>> getJuice() throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        int index = 0;
        while (true){
            Map<String, Object> var = getList(Utils.JUICE, "" + (++index ));
            if (var != null){
                list.add(var);
            } else {
                break;
            }
        }
        for (Map<String, Object> stringObjectMap : list) {
            System.out.println(stringObjectMap.toString());
        }
        System.out.println("search from juice!");
        return list;
    }

    public List<Map<String, Object>> getCookies() throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        int index = 0;
        while (true){
            Map<String, Object> var = getList(Utils.COOKIES, "" + (++index ));
            if (var != null){
                list.add(var);
            } else {
                break;
            }
        }
        for (Map<String, Object> stringObjectMap : list) {
            System.out.println(stringObjectMap.toString());
        }
        System.out.println("search from cookies!");
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
}
