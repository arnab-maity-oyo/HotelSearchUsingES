package com.arnab.hotelsearchtask.hotel_search_task.controller;


import com.arnab.hotelsearchtask.hotel_search_task.model.Hotel;
import com.arnab.hotelsearchtask.hotel_search_task.service.HotelsearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/hotelsearchinfo")
public class HotelsearchController {

    @Autowired
    private HotelsearchService hotelsearchService;

    @PostMapping("/addhotels")
    public String addHotel(@RequestBody Hotel hotel)  {
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
