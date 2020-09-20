package com.arnab.hotelsearchtask.hotel_search_task.controller;

import com.arnab.hotelsearchtask.hotel_search_task.exception.DocumentNotFoundException;
import com.arnab.hotelsearchtask.hotel_search_task.model.Country;
import com.arnab.hotelsearchtask.hotel_search_task.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/country")
public class CountryController {

    @Autowired
    private CountryService countryService;

    @PostMapping("/addcountry")
    public String addCountrytoES(@RequestBody Country country)  {
        return countryService.addCountry(country);
    }

    @GetMapping(value ="/allcountries", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Country> getAllCountry(){
        return countryService.getAllCountriesInfo();
    }

    @Cacheable(value = "countries", key="#country_id")
    @GetMapping(value ="/allcountries/{country_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Country getCountryByID(@PathVariable String country_id) throws DocumentNotFoundException {
        System.out.println("Fetched from ES database for country_ID : " + country_id);
        return countryService.getCountryInfo(country_id);
    }

}
