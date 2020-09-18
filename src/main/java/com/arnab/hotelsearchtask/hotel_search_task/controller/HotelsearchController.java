package com.arnab.hotelsearchtask.hotel_search_task.controller;


import com.arnab.hotelsearchtask.hotel_search_task.model.Hotel;
import com.arnab.hotelsearchtask.hotel_search_task.service.HotelsearchService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/hotelsearchinfo")
public class HotelsearchController {

    @Autowired
    private HotelsearchService hotelsearchService;

//    RestHighLevelClient client = new RestHighLevelClient(
//            RestClient.builder(new HttpHost("localhost", 9200, "http")));

    @PostMapping("/addhotels")
    public String addHotel(@RequestBody Hotel hotel) throws IOException {
        return hotelsearchService.addHoteltoElastic(hotel);
    }

    @GetMapping(value ="/allhotels", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Hotel> getAllHotels(){
        return hotelsearchService.getAllHotelsInfo();
    }

    @GetMapping(value ="/allhotels/{country_id}/{city_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Hotel> getHotelsByCountryIDAndCityID(@PathVariable String country_id, @PathVariable String city_id){
        return hotelsearchService.getHotelsDataByCountryAndCity(country_id, city_id);
    }

}
