package org.acme;

import org.acme.Service.DataService;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;

@Path("/api")
public class ExampleResource {

    @Inject
    DataService dataService;

    @GET
    @Path("/{keyWord}")
    @Produces(MediaType.TEXT_PLAIN)
    public List<Map<String, Object>> getData(@PathParam String keyWord){
        List<Map<String, Object>> list = null;
        try{
            list = dataService.getData(keyWord);
        } catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
}