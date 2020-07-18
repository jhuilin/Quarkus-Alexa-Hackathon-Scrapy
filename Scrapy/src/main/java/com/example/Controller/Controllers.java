package com.example.Controller;

import com.example.Service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;
import java.util.Map;

@RestController
@EnableWebMvc
@RequestMapping("api/")
public class Controllers {

    @Autowired
    DataService dataService;

    @RequestMapping(path="getBeef", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Map<String, Object>> > getBeef(){
        List<Map<String, Object>> beef = null;
        try{
            beef = dataService.getBeef();
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(beef, beef == null? HttpStatus.NOT_FOUND:HttpStatus.OK);
    }

    @RequestMapping(path="getMilk", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Map<String, Object>> > getMilk(){
        List<Map<String, Object>> milk = null;
        try{
            milk = dataService.getMilk();
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(milk, milk == null? HttpStatus.NOT_FOUND:HttpStatus.OK);
    }
}
