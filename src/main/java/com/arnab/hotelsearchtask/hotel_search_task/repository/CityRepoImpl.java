package com.arnab.hotelsearchtask.hotel_search_task.repository;

import com.arnab.hotelsearchtask.hotel_search_task.exception.DocumentNotFoundException;
import com.arnab.hotelsearchtask.hotel_search_task.model.City;
import com.arnab.hotelsearchtask.hotel_search_task.model.Country;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
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
public class CityRepoImpl implements CityRepo, CountryRepo {

    @Autowired
    private ObjectMapper objectMapper;

    RestHighLevelClient client = new RestHighLevelClient(
            RestClient.builder(new HttpHost("localhost", 9200, "http")));

    @Override
    public String AddCitytoElastic(City city) throws DocumentNotFoundException {
        Country country_info = new Country();
        String country_id_info = city.getCountry_id();
        try {
            country_info = getCountryInfoFromElastic(country_id_info);
        } catch (DocumentNotFoundException e) {
            e.printStackTrace();
        }

        if (country_info.getCountry_id() != null) {

            IndexRequest request = new IndexRequest(city_INDEX);
            request.id(city.getCity_id());
            try {
                request.source(new ObjectMapper().writeValueAsString(city), XContentType.JSON);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            IndexResponse indexResponse = null;
            try {
                indexResponse = client.index(request, RequestOptions.DEFAULT);
                System.out.println("response id: " + indexResponse.getId());
            } catch (Exception e) {
                e.printStackTrace();
            }

            return indexResponse.getResult().name();

        } else {
            throw new DocumentNotFoundException("Country ID not found");
        }

    }

    @Override
    public List<City> findAllCitiesFromElastic() {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(city_INDEX);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchRequest.source(searchSourceBuilder);
        List<City> citiesList = new ArrayList<>();
        SearchResponse searchResponse = null;
        try {
            searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            if (searchResponse.getHits().getTotalHits().value > 0) {
                SearchHit[] searchHit = searchResponse.getHits().getHits();
                for (SearchHit hit : searchHit) {
                    Map<String, Object> map = hit.getSourceAsMap();
                    citiesList.add(objectMapper.convertValue(map, City.class));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return citiesList;
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
