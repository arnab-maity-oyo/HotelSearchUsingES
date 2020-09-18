package com.arnab.hotelsearchtask.hotel_search_task.controller;


import com.arnab.hotelsearchtask.hotel_search_task.model.Country;
import com.arnab.hotelsearchtask.hotel_search_task.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/country")
public class CountryController {

    @Autowired
    private CountryService countryService;

    @PostMapping("/addcountry")
    public String addCountrytoES(@RequestBody Country country) throws IOException {
        return countryService.addCountry(country);
    }

    @GetMapping(value ="/allcountries", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Country> getAllCountry(){
        return countryService.getAllCountriesInfo();
    }
}
