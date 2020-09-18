package com.arnab.hotelsearchtask.hotel_search_task.repository;

import com.arnab.hotelsearchtask.hotel_search_task.model.Hotel;
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
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.arnab.hotelsearchtask.hotel_search_task.util.Constant.hotel_INDEX;


@Component
public class HotelsearchRepoImpl implements HotelsearchRepo {

    @Autowired
    private ObjectMapper objectMapper;

    RestHighLevelClient client = new RestHighLevelClient(
            RestClient.builder(new HttpHost("localhost", 9200, "http")));


    @Override
    public String AddHoteltoES(Hotel hotel) {
        IndexRequest request = new IndexRequest(hotel_INDEX);

        try {
            request.id(hotel.getHotel_id());
            request.source(new ObjectMapper().writeValueAsString(hotel), XContentType.JSON);
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


}