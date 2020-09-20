package com.arnab.hotelsearchtask.hotel_search_task;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class HotelSearchTaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(HotelSearchTaskApplication.class, args);
    }

}
