package com.arnab.hotelsearchtask.hotel_search_task.repository;

import com.arnab.hotelsearchtask.hotel_search_task.model.Hotel;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.util.List;

@Repository
public interface HotelsearchRepo {
    List<Hotel> findAllHotelsFromElastic();

    List<Hotel> findAllHotelDataByCountryAndCityFromElastic(String country_id, String city_id);


    String AddHoteltoES(Hotel hotel) throws IOException;
}
