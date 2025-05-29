package com.example.middle_roadmap.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.middle_roadmap.dto.Article;
import java.util.List;

import java.io.IOException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ElasticsearchClient esClient;
    private final String INDEX_NAME = "articles";

    public void saveArticle(Article article) throws IOException {
        esClient.index(i -> i
                .index(INDEX_NAME)
                .id(String.valueOf(article.getId()))
                .document(article)
        );
    }

    public List<Article> searchByTitle(String keyword) throws IOException {
        SearchResponse<Article> response = esClient.search(s -> s
                        .index(INDEX_NAME)
                        .query(q -> q
                                .match(m -> m
                                        .field("title")
                                        .query(keyword)
                                )
                        ),
                Article.class
        );

        return response.hits().hits().stream()
                .map(Hit::source)
                .collect(Collectors.toList());
    }
}
