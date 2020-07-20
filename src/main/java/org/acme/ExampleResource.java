package org.acme;

import org.acme.Service.DataService;

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
    @Path("/beef")
    @Produces(MediaType.TEXT_PLAIN)
    public List<Map<String, Object>> getBeef(){
        List<Map<String, Object>> beef = null;
        try{
            beef = dataService.getBeef();
        } catch (Exception e){
            e.printStackTrace();
        }
        return beef;
    }

    @GET
    @Path("/milk")
    @Produces(MediaType.TEXT_PLAIN)
    public List<Map<String, Object>> getMilk(){
        List<Map<String, Object>> milk = null;
        try{
            milk = dataService.getMilk();
        } catch (Exception e){
            e.printStackTrace();
        }
        return milk;
    }

    @GET
    @Path("/cookies")
    @Produces(MediaType.TEXT_PLAIN)
    public List<Map<String, Object>> getCookies(){
        List<Map<String, Object>> list = null;
        try{
            list = dataService.getCookies();
        } catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    @GET
    @Path("/juice")
    @Produces(MediaType.TEXT_PLAIN)
    public List<Map<String, Object>> getJuice(){
        List<Map<String, Object>> list = null;
        try{
            list = dataService.getJuice();
        } catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
}