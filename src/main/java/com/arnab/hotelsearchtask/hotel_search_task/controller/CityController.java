package com.arnab.hotelsearchtask.hotel_search_task.controller;

import com.arnab.hotelsearchtask.hotel_search_task.exception.DocumentNotFoundException;
import com.arnab.hotelsearchtask.hotel_search_task.model.City;
import com.arnab.hotelsearchtask.hotel_search_task.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/city")
public class CityController {
    @Autowired
    private CityService cityService;

    @PostMapping("/addcity")
    public String addCCitytoES(@RequestBody City city) throws DocumentNotFoundException {
        return cityService.addCity(city);
    }

    @GetMapping(value ="/allcities", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<City> getAllCity(){
        return cityService.getAllCitiesInfo();
    }


    @Cacheable(value = "cities", key = "#city_id")
    @GetMapping(value ="/allcities/{city_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public City getcityByID(@PathVariable String city_id)
    {
        System.out.println("Fetched from ES database for city_ID : " + city_id);
        return cityService.getcityInfo(city_id);
    }

}
