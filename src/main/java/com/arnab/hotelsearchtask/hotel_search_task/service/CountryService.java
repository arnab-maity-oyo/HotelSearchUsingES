package com.arnab.hotelsearchtask.hotel_search_task.service;


import com.arnab.hotelsearchtask.hotel_search_task.exception.DocumentNotFoundException;
import com.arnab.hotelsearchtask.hotel_search_task.model.Country;
import com.arnab.hotelsearchtask.hotel_search_task.model.Hotel;
import com.arnab.hotelsearchtask.hotel_search_task.repository.CountryRepo;
import com.arnab.hotelsearchtask.hotel_search_task.repository.HotelsearchRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class CountryService {


    @Qualifier("countryRepoImpl")
    @Autowired
    private CountryRepo countryRepository;

    public String addCountry(Country country) {
        return countryRepository.AddCountrytoElastic(country);
    }

    public List<Country> getAllCountriesInfo() {

        return countryRepository.findAllCountriesFromElastic();
    }

    public Country getCountryInfo(String country_id) throws DocumentNotFoundException {
        return countryRepository.getCountryInfoFromElastic(country_id);
    }
}
