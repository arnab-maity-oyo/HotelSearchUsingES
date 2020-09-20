package com.arnab.hotelsearchtask.hotel_search_task.repository;


import com.arnab.hotelsearchtask.hotel_search_task.exception.DocumentNotFoundException;
import com.arnab.hotelsearchtask.hotel_search_task.model.Country;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CountryRepo {

    String AddCountrytoElastic(Country country);

    List<Country> findAllCountriesFromElastic();

    Country getCountryInfoFromElastic(String country_id) throws DocumentNotFoundException;
}
