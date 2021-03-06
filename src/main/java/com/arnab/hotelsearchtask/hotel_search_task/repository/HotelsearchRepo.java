package com.arnab.hotelsearchtask.hotel_search_task.repository;

import com.arnab.hotelsearchtask.hotel_search_task.exception.DocumentNotFoundException;
import com.arnab.hotelsearchtask.hotel_search_task.model.Hotel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelsearchRepo {
    List<Hotel> findAllHotelsFromElastic();

    List<Hotel> findAllHotelDataByCountryAndCityFromElastic(String country_id, String city_id);


    String AddHoteltoES(Hotel hotel) throws DocumentNotFoundException;

    Hotel updateHotelInfotoElastic(String hotel_id, Hotel hotel) throws DocumentNotFoundException;

    Hotel getHotelInfoByIDfromElastic(String hotel_id) throws DocumentNotFoundException;

    List<String> getHotelsCountByStatusType();

    void deleteHotelByIDfromElastic(String hotel_id) throws DocumentNotFoundException;
}
