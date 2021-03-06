package com.arnab.hotelsearchtask.hotel_search_task.service;

import com.arnab.hotelsearchtask.hotel_search_task.exception.DocumentNotFoundException;
import com.arnab.hotelsearchtask.hotel_search_task.model.City;
import com.arnab.hotelsearchtask.hotel_search_task.repository.CityRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class CityService {

    @Qualifier("cityRepoImpl")
    @Autowired
    private CityRepo cityRepository;

    public String addCity(City city) throws DocumentNotFoundException {
        return cityRepository.AddCitytoElastic(city);
    }

    public List<City> getAllCitiesInfo() {
        return cityRepository.findAllCitiesFromElastic();
    }

    public City getcityInfo(String city_id) {
        return cityRepository.getcityInfoFromElastic(city_id);
    }
}
