package com.arnab.hotelsearchtask.hotel_search_task.controller;


import com.arnab.hotelsearchtask.hotel_search_task.model.City;
import com.arnab.hotelsearchtask.hotel_search_task.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/city")
public class CityController {
    @Autowired
    private CityService cityService;

    @PostMapping("/addcity")
    public String addCCitytoES(@RequestBody City city) throws IOException {
        return cityService.addCity(city);
    }

    @GetMapping(value ="/allcities", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<City> getAllCity(){
        return cityService.getAllCitiesInfo();
    }

}
