package com.arnab.hotelsearchtask.hotel_search_task.controller;


import com.arnab.hotelsearchtask.hotel_search_task.exception.DocumentNotFoundException;
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
    public String addHotel(@RequestBody Hotel hotel)
            throws DocumentNotFoundException {
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

    @GetMapping(value="/allhotels/{hotel_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Hotel getHotelByID(@PathVariable String hotel_id) throws DocumentNotFoundException
    {
        return hotelsearchService.getHotelInfoByID(hotel_id);
    }

    @PutMapping(value = "/updatehotel/{hotel_id}")
    public String updateHotel(@PathVariable String hotel_id, @RequestBody Hotel hotel) throws Exception {
        return hotelsearchService.updateHotelInfo(hotel_id, hotel);
    }

}
