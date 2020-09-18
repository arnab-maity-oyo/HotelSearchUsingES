package com.arnab.hotelsearchtask.hotel_search_task.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

@Document(indexName = "city_oyo")
public class City {
    @Id
    @Field(value="city_id")
    private String city_id;
    @Field(value="city_name")
    private String city_name;
    @Field(value="country_id")
    private String country_id;

    public City() {
    }

    public City(String city_id, String city_name, String country_id) {
        this.city_id = city_id;
        this.city_name = city_name;
        this.country_id = country_id;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getCountry_id() {
        return country_id;
    }

    public void setCountry_id(String country_id) {
        this.country_id = country_id;
    }
}
