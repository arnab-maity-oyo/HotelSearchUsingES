package com.arnab.hotelsearchtask.hotel_search_task.repository;

import com.arnab.hotelsearchtask.hotel_search_task.exception.DocumentNotFoundException;
import com.arnab.hotelsearchtask.hotel_search_task.model.City;
import com.arnab.hotelsearchtask.hotel_search_task.model.Country;
import com.arnab.hotelsearchtask.hotel_search_task.model.Hotel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.arnab.hotelsearchtask.hotel_search_task.util.Constant.*;


@Component
public class HotelsearchRepoImpl implements HotelsearchRepo, CountryRepo, CityRepo {

    @Autowired
    private ObjectMapper objectMapper;

    RestHighLevelClient client = new RestHighLevelClient(
            RestClient.builder(new HttpHost("localhost", 9200, "http")));


    @Override
    public String AddHoteltoES(Hotel hotel)
            throws DocumentNotFoundException {

        Country country_info = new Country();
        City city_info = new City();
        String country_id_info = hotel.getCountry_id();
        String city_id_info = hotel.getCity_id();

        try {
            country_info = getCountryInfoFromElastic(country_id_info);
        } catch (DocumentNotFoundException e) {
            e.printStackTrace();
        }

        city_info = getcityInfoFromElastic(city_id_info);

        if (country_info.getCountry_id() == null && city_info.getCity_id() == null) {
            throw new DocumentNotFoundException("Country_ID and City_ID Not Found");
        } else {
            if (country_info.getCountry_id() == null && city_info.getCity_id() != null) {
                throw new DocumentNotFoundException("Country_ID Not Found");

            } else {
                if (country_info.getCountry_id() != null && city_info.getCity_id() == null) {
                    throw new DocumentNotFoundException("City_ID Not Found");

                } else {
                    IndexRequest request = new IndexRequest(hotel_INDEX);

                    try {
                        request.id(hotel.getHotel_id());
                        request.source(new ObjectMapper().writeValueAsString(hotel), XContentType.JSON);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    IndexResponse indexResponse = null;
                    try {
                        indexResponse = client.index(request, RequestOptions.DEFAULT);
                        System.out.println("response id: " + indexResponse.getId()+" inserted");

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return indexResponse.getResult().name();
                }
            }
        }


    }

    @Override
    public Hotel getHotelInfoByIDfromElastic(String hotel_id) throws DocumentNotFoundException {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(hotel_INDEX);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.boolQuery().must(
                QueryBuilders.termQuery("hotel_id.keyword", hotel_id)));

        searchRequest.source(searchSourceBuilder);
        Hotel hotelInfo = new Hotel();
        SearchResponse searchResponse = null;
        try {
            searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            if (searchResponse.getHits().getTotalHits().value > 0) {
                SearchHit[] searchHit = searchResponse.getHits().getHits();
                for (SearchHit hit : searchHit) {
                    Map<String, Object> map = hit.getSourceAsMap();
                    hotelInfo = objectMapper.convertValue(map, Hotel.class);
                }
            }
            else
            {
                throw  new DocumentNotFoundException("Hotel_ID does not exist");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return hotelInfo;
    }


    @Override
    public String updateHotelInfotoElastic(String hotel_id, Hotel hotel) throws DocumentNotFoundException {
        Hotel hoteldocument = new Hotel();
        hoteldocument = getHotelInfoByIDfromElastic(hotel_id);
        Country country_info = new Country();
        City city_info = new City();
        String country_id_info = hotel.getCountry_id();
        String city_id_info = hotel.getCity_id();

        try {
            country_info = getCountryInfoFromElastic(country_id_info);
        } catch (DocumentNotFoundException e) {
            e.printStackTrace();
        }

        city_info = getcityInfoFromElastic(city_id_info);


        if (hoteldocument.getHotel_id() == null && country_info.getCountry_id() == null && city_info.getCity_id() == null) {
            throw new DocumentNotFoundException("Hotel ID, Country_ID and City_ID does not exist");
        } else {
            if (hoteldocument.getHotel_id() != null && country_info.getCountry_id() == null && city_info.getCity_id() == null) {
                throw new DocumentNotFoundException("Country_ID and City_ID does not exist");
            } else {
                if (hoteldocument.getHotel_id() != null && country_info.getCountry_id() == null && city_info.getCity_id() != null) {
                    throw new DocumentNotFoundException("Country_ID does not exist");
                } else {
                    if (hoteldocument.getHotel_id() != null && country_info.getCountry_id() != null && city_info.getCity_id() == null) {
                        throw new DocumentNotFoundException("City_ID does not exist");
                    } else {
                        if (hoteldocument.getHotel_id() == null && country_info.getCountry_id() == null && city_info.getCity_id() != null) {
                            throw new DocumentNotFoundException("Hotel_ID and Country_ID does not exist");
                        } else {
                            if (hoteldocument.getHotel_id() == null && country_info.getCountry_id() != null && city_info.getCity_id() != null) {
                                throw new DocumentNotFoundException("Hotel_ID does not exist");
                            } else {
                                if (hoteldocument.getHotel_id() == null && country_info.getCountry_id() != null && city_info.getCity_id() == null) {
                                    throw new DocumentNotFoundException("Hotel_ID and City_ID does not exist");
                                } else {
                                    UpdateResponse updateResponse = null;
                                    try {

                                        UpdateRequest updateRequest = new UpdateRequest(hotel_INDEX, hotel_TYPE, hoteldocument.getHotel_id());
                                        hotel.setHotel_id(hotel_id);
                                        updateRequest.doc(new ObjectMapper().writeValueAsString(hotel), XContentType.JSON);
                                        updateResponse = client.update(updateRequest, RequestOptions.DEFAULT);
                                        System.out.println("response id: " + updateResponse.getId()+" updated");
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    return updateResponse.getResult().name();

                                }
                            }
                        }
                    }
                }
            }
        }
    }


    @Override
    public List<Hotel> findAllHotelsFromElastic() {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(hotel_INDEX);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchRequest.source(searchSourceBuilder);
        List<Hotel> hotelsList = new ArrayList<>();
        SearchResponse searchResponse = null;
        try {
            searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            if (searchResponse.getHits().getTotalHits().value > 0) {
                SearchHit[] searchHit = searchResponse.getHits().getHits();
                for (SearchHit hit : searchHit) {
                    Map<String, Object> map = hit.getSourceAsMap();
                    hotelsList.add(objectMapper.convertValue(map, Hotel.class));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return hotelsList;
    }


    @Override
    public List<Hotel> findAllHotelDataByCountryAndCityFromElastic(String country_id, String city_id) {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(hotel_INDEX);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.boolQuery().must(
                QueryBuilders.termQuery("country_id.keyword", country_id))
                .must(QueryBuilders.matchQuery("city_id.keyword", city_id)));

        searchRequest.source(searchSourceBuilder);
        List<Hotel> hotelsList = new ArrayList<>();

        try {
            SearchResponse searchResponse = null;
            searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            if (searchResponse.getHits().getTotalHits().value > 0) {
                SearchHit[] searchHit = searchResponse.getHits().getHits();
                for (SearchHit hit : searchHit) {
                    Map<String, Object> map = hit.getSourceAsMap();
                    hotelsList.add(objectMapper.convertValue(map, Hotel.class));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return hotelsList;
    }


    @Override
    public String AddCitytoElastic(City city) throws DocumentNotFoundException {
        return null;
    }

    @Override
    public List<City> findAllCitiesFromElastic() {
        return null;
    }

    @Override
    public City getcityInfoFromElastic(String city_id) {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(city_INDEX);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.boolQuery().must(
                QueryBuilders.termQuery("city_id.keyword", city_id)));

        searchRequest.source(searchSourceBuilder);

        City cityInfoList = new City();
        SearchResponse searchResponse = null;
        try {
            searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            if (searchResponse.getHits().getTotalHits().value > 0) {
                SearchHit[] searchHit = searchResponse.getHits().getHits();
                for (SearchHit hit : searchHit) {
                    Map<String, Object> map = hit.getSourceAsMap();
                    cityInfoList = (objectMapper.convertValue(map, City.class));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return cityInfoList;
    }

    @Override
    public String AddCountrytoElastic(Country country) {
        return null;
    }

    @Override
    public List<Country> findAllCountriesFromElastic() {
        return null;
    }

    @Override
    public Country getCountryInfoFromElastic(String country_id) throws DocumentNotFoundException {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(country_INDEX);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.boolQuery().must(
                QueryBuilders.termQuery("country_id.keyword", country_id)));

        searchRequest.source(searchSourceBuilder);

        Country countryInfo = new Country();
        SearchResponse searchResponse = null;
        try {
            searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            if (searchResponse.getHits().getTotalHits().value > 0) {
                SearchHit[] searchHit = searchResponse.getHits().getHits();
                for (SearchHit hit : searchHit) {
                    Map<String, Object> map = hit.getSourceAsMap();
                    countryInfo = objectMapper.convertValue(map, Country.class);
                }
            } else {
                throw new DocumentNotFoundException("Country Not present in the database");
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return countryInfo;
    }
}