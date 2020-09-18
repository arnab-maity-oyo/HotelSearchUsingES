package com.arnab.hotelsearchtask.hotel_search_task.repository;

import com.arnab.hotelsearchtask.hotel_search_task.model.City;
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

import static com.arnab.hotelsearchtask.hotel_search_task.util.Constant.city_INDEX;


@Component
public class CityRepoImpl implements CityRepo{

    @Autowired
    private ObjectMapper objectMapper;

    RestHighLevelClient client = new RestHighLevelClient(
            RestClient.builder(new HttpHost("localhost", 9200, "http")));

    @Override
    public String AddCitytoElastic(City city)  {
        IndexRequest request = new IndexRequest(city_INDEX);
        try {
            request.id(city.getCity_id());
            request.source(new ObjectMapper().writeValueAsString(city), XContentType.JSON);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        IndexResponse indexResponse = null;
        try {
            indexResponse = client.index(request, RequestOptions.DEFAULT);
            System.out.println("response id: " + indexResponse.getId());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return indexResponse.getResult().name();
    }

    @Override
    public List<City> findAllCitiesFromElastic(){
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

}
