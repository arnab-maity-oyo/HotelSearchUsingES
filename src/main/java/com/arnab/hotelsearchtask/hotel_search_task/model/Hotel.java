package com.arnab.hotelsearchtask.hotel_search_task.model;

import com.arnab.hotelsearchtask.hotel_search_task.enums.HotelStatus;
import com.arnab.hotelsearchtask.hotel_search_task.enums.HotelType;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import java.io.Serializable;


@Document(indexName = "hotels_oyo")
public class Hotel implements Serializable {
    @Id
    @Field(value = "hotel_id")
    private String hotel_id;
    @Field(value = "hotel_type")
    private HotelType hotel_type;
    @Field(value="hotel_status")
    private HotelStatus hotel_status;
    @Field(value="country_id")
    private String country_id;
    @Field(value = "city_id")
    private String city_id;

    public Hotel() {
    }

    public Hotel(String hotel_id, HotelType hotel_type, HotelStatus hotel_status, String country_id, String city_id) {
        this.hotel_id = hotel_id;
        this.hotel_type = hotel_type;
        this.hotel_status = hotel_status;
        this.country_id = country_id;
        this.city_id = city_id;
    }

    public String getHotel_id() {
        return hotel_id;
    }

    public void setHotel_id(String hotel_id) {
        this.hotel_id = hotel_id;
    }

    public HotelType getHotel_type() {
        return hotel_type;
    }

    public void setHotel_type(HotelType hotel_type) {
        this.hotel_type = hotel_type;
    }

    public HotelStatus getHotel_status() {
        return hotel_status;
    }

    public void setHotel_status(HotelStatus hotel_status) {
        this.hotel_status = hotel_status;
    }

    public String getCountry_id() {
        return country_id;
    }

    public void setCountry_id(String country_id) {
        this.country_id = country_id;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }


}
