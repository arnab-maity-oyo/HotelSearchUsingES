package com.arnab.hotelsearchtask.hotel_search_task.service;

import com.arnab.hotelsearchtask.hotel_search_task.exception.DocumentNotFoundException;
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

    public String addHoteltoElastic(Hotel hotel)
            throws DocumentNotFoundException {
        return hotelRepository.AddHoteltoES(hotel);
    }

    public Hotel updateHotelInfo(String hotel_id, Hotel hotel) throws DocumentNotFoundException {
        return hotelRepository.updateHotelInfotoElastic(hotel_id, hotel);
    }

    public Hotel getHotelInfoByID(String hotel_id)throws DocumentNotFoundException {
        return  hotelRepository.getHotelInfoByIDfromElastic(hotel_id);
    }

    public List<String> getHotelsCount() {
        return hotelRepository.getHotelsCountByStatusType();
    }

    public void deleteHotelByID(String hotel_id) throws DocumentNotFoundException {
        hotelRepository.deleteHotelByIDfromElastic(hotel_id);
    }
}
