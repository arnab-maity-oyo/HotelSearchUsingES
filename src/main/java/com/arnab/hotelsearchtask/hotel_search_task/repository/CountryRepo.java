package com.arnab.hotelsearchtask.hotel_search_task.repository;


import com.arnab.hotelsearchtask.hotel_search_task.model.Country;
import com.arnab.hotelsearchtask.hotel_search_task.model.Hotel;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;

@Repository
public interface CountryRepo {

    String AddCountrytoElastic(Country country);

    List<Country> findAllCountriesFromElastic();
}
