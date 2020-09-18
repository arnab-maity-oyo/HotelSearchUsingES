package com.arnab.hotelsearchtask.hotel_search_task.repository;

import com.arnab.hotelsearchtask.hotel_search_task.model.Country;
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

import static com.arnab.hotelsearchtask.hotel_search_task.util.Constant.country_INDEX;


@Component
public class CountryRepoImpl implements CountryRepo {
    @Autowired
    private ObjectMapper objectMapper;

    RestHighLevelClient client = new RestHighLevelClient(
            RestClient.builder(new HttpHost("localhost", 9200, "http")));

    @Override
    public String AddCountrytoElastic(Country country) throws IOException {
        IndexRequest request = new IndexRequest(country_INDEX);
        request.id(country.getCountry_id());
        request.source(new ObjectMapper().writeValueAsString(country), XContentType.JSON);
        IndexResponse indexResponse = client.index(request, RequestOptions.DEFAULT);
        System.out.println("response id: " + indexResponse.getId());
        return indexResponse.getResult().name();
    }

    @Override
    public List<Country> findAllCountriesFromElastic(){
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(country_INDEX);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchRequest.source(searchSourceBuilder);
        List<Country> countriesList = new ArrayList<>();
        SearchResponse searchResponse = null;
        try {
            searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            if (searchResponse.getHits().getTotalHits().value > 0) {
                SearchHit[] searchHit = searchResponse.getHits().getHits();
                for (SearchHit hit : searchHit) {
                    Map<String, Object> map = hit.getSourceAsMap();
                    countriesList.add(objectMapper.convertValue(map, Country.class));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return countriesList;
    }
}
