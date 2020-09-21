package com.arnab.hotelsearchtask.hotel_search_task.controller;

import com.arnab.hotelsearchtask.hotel_search_task.enums.HotelStatus;
import com.arnab.hotelsearchtask.hotel_search_task.enums.HotelType;
import com.arnab.hotelsearchtask.hotel_search_task.exception.DocumentNotFoundException;
import com.arnab.hotelsearchtask.hotel_search_task.model.Hotel;
import com.arnab.hotelsearchtask.hotel_search_task.service.HotelsearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
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

    @Cacheable(value="hotels", key="#hotel_id")
    @GetMapping(value="/allhotels/{hotel_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Hotel getHotelByID(@PathVariable String hotel_id) throws DocumentNotFoundException
    {
        System.out.println("Fetched from ES database for hotel_ID : "+hotel_id);
        return hotelsearchService.getHotelInfoByID(hotel_id);
    }

    @PutMapping(value = "/updatehotel/{hotel_id}")
    @CachePut(value = "hotels", key="#hotel_id")
    public Hotel updateHotel(@PathVariable String hotel_id, @RequestBody Hotel hotel) throws Exception {
        return hotelsearchService.updateHotelInfo(hotel_id, hotel);
    }

    @GetMapping(value="/allhotels/show_count", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> getCountofHotels()
    {
        return hotelsearchService.getHotelsCount();
    }

    @DeleteMapping(value = "/delete_hotel/{hotel_id}")
    @CacheEvict(value="hotels", allEntries = true)
    public void deleteHotel(@PathVariable String hotel_id) throws DocumentNotFoundException
    {
         hotelsearchService.deleteHotelByID(hotel_id);
    }


}
