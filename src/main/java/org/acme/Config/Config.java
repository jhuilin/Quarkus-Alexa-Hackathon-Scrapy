package org.acme.Config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;


@ApplicationScoped
public class Config {

    private String host = "3.22.175.238";    // set your own elastic search url


    @Produces
    RestHighLevelClient client = new RestHighLevelClient(
            RestClient.builder(
                    new HttpHost(host, 9200, "http")));


}
