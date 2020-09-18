package com.arnab.hotelsearchtask.hotel_search_task.service;

import com.arnab.hotelsearchtask.hotel_search_task.model.Hotel;
import com.arnab.hotelsearchtask.hotel_search_task.repository.HotelsearchRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.util.List;

@Service
public class HotelsearchService {

    @Qualifier("hotelsearchRepoImpl")
    @Autowired
    private HotelsearchRepo hotelRepository;

    public List<Hotel> getAllHotelsInfo() {

        return hotelRepository.findAllHotelsFromElastic();
    }


    public List<Hotel> getHotelsDataByCountryAndCity(String country_id, String city_id) {
        return hotelRepository.findAllHotelDataByCountryAndCityFromElastic(country_id, city_id);

    }

    public String addHoteltoElastic(Hotel hotel) throws IOException {
        return hotelRepository.AddHoteltoES(hotel);
    }
}
