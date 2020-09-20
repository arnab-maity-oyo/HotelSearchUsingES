package com.arnab.hotelsearchtask.hotel_search_task.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import java.io.Serializable;

@Document(indexName = "country_oyo")
public class Country implements Serializable {

    @Id
    @Field(value = "country_id")
    private String country_id;
    @Field(value = "country_name")
    private String country_name;

    public Country() {
    }

    public Country(String country_id, String country_name) {
        this.country_id = country_id;
        this.country_name = country_name;
    }

    public String getCountry_id() {
        return country_id;
    }

    public void setCountry_id(String country_id) {
        this.country_id = country_id;
    }

    public String getCountry_name() {
        return country_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }
}
