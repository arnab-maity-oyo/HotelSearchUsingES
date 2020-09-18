package com.arnab.hotelsearchtask.hotel_search_task.repository;

import com.arnab.hotelsearchtask.hotel_search_task.model.City;
import com.arnab.hotelsearchtask.hotel_search_task.model.Country;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;

@Repository
public interface CityRepo {
    String AddCitytoElastic(City city) ;

    List<City> findAllCitiesFromElastic();
}
