package org.acme.Config;

import org.apache.http.HttpHost;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;


@ApplicationScoped
public class Config {


    @Produces
    RestHighLevelClient client = new RestHighLevelClient(
            RestClient.builder(
                    new HttpHost("3.22.175.238", 9200, "http")));


}
