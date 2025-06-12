package com.example.middle_roadmap.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class ElasticsearchConfig {
    @Bean
    public ElasticsearchClient elasticsearchClient() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        JacksonJsonpMapper jsonpMapper = new JacksonJsonpMapper(mapper);

        RestClient restClient = RestClient.builder(
                new HttpHost("localhost", 9200)).build();

        ElasticsearchTransport transport = new RestClientTransport(
                restClient, jsonpMapper);

        return new ElasticsearchClient(transport);
    }
}
