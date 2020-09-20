package com.arnab.hotelsearchtask.hotel_search_task.repository;

import com.arnab.hotelsearchtask.hotel_search_task.exception.DocumentNotFoundException;
import com.arnab.hotelsearchtask.hotel_search_task.model.City;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepo {
    String AddCitytoElastic(City city) throws DocumentNotFoundException;

    List<City> findAllCitiesFromElastic();

    City getcityInfoFromElastic(String city_id);
}
