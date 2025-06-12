package com.example.middle_roadmap.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch.core.CountResponse;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.json.JsonData;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.middle_roadmap.dto.Article;

import java.time.LocalDateTime;
import java.util.List;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ElasticsearchClient esClient;
    private final String INDEX_NAME = "articles";

    public void saveArticle(Article article) throws IOException {
        article.setCreatedAt(LocalDateTime.now());
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

    @PostConstruct
    public void createIndex() throws IOException {
        boolean exists = esClient.indices().exists(e -> e.index(INDEX_NAME)).value();
        if (!exists) {
            esClient.indices().create(c -> c
                    .index(INDEX_NAME)
                    .mappings(m -> m
                            .properties("title", p -> p.text(t -> t.analyzer("standard")))
                            .properties("content", p -> p.text(t -> t.analyzer("standard")))
                            .properties("author", p -> p.text(t -> t.analyzer("standard")))
                            .properties("viewCnt", p -> p.integer(t -> t.nullValue(0)))
                            .properties("createdAt", p -> p.date(d -> d.format("strict_date_optional_time")))
                    )
            );
        }
    }

    public List<Article> searchWithAdvanced(String keyword, String fromDate, String toDate, int page, int size, String sortBy, String sortDir) throws IOException {
        int from = page * size;

        SearchResponse<Article> response = esClient.search(s -> s
                        .index(INDEX_NAME)
                        .from(from)
                        .size(size)
                        .sort(sort -> sort
                                .field(f -> f
                                        .field(sortBy)
                                        .order("asc".equalsIgnoreCase(sortDir) ? SortOrder.Asc : SortOrder.Desc)
                                )
                        )
                        .query(q -> q
                                .bool(b -> b
                                        .must(m -> m
                                                .match(mt -> mt.field("title").query(keyword))
                                        )
                                        .filter(f -> f
                                                .range(r -> r
                                                        .field("createdAt")
                                                        .gte(JsonData.of(fromDate))
                                                        .lte(JsonData.of(toDate))
                                                )
                                        )
                                )
                        ),
                Article.class
        );

        return response.hits().hits().stream()
                .map(Hit::source)
                .collect(Collectors.toList());
    }

    public List<Article> searchByTitleAndDate(String keyword, String fromDate, String toDate) throws IOException {
        SearchResponse<Article> response = esClient.search(s -> s
                        .index(INDEX_NAME)
                        .query(q -> q
                                .bool(b -> b
                                        .must(m -> m.multiMatch(t -> t.fields("title", "content").query(keyword)))
                                        .must(m -> m.match(t -> t.field("author").query("truongvd")))
                                        .filter(f -> f.range(r -> r
                                                .field("createdAt")
                                                .gte(JsonData.of(fromDate))
                                                .lte(JsonData.of(toDate))
                                        ))
                                )
                        ),
                Article.class
        );

        return response.hits().hits().stream()
                .map(Hit::source)
                .collect(Collectors.toList());
    }

    public void updateArticle(String id, String newTitle) throws IOException {
        esClient.update(u -> u
                        .index(INDEX_NAME)
                        .id(id)
                        .doc(Map.of("title", newTitle)),
                Article.class
        );
    }

    public void deleteArticle(String id) throws IOException {
        esClient.delete(d -> d
                .index(INDEX_NAME)
                .id(id)
        );
    }

    public void deleteIndex() throws IOException {
        esClient.indices().delete(d -> d.index(INDEX_NAME));
    }

    public long countArticlesByTitle(String keyword) throws IOException {
        CountResponse count = esClient.count(c -> c
                .index(INDEX_NAME)
                .query(q -> q
                        .match(m -> m
                                .field("title")
                                .query(keyword)
                        )
                )
        );

        return count.count();
    }

    public List<Article> findAll() throws IOException {
        SearchResponse<Article> response = esClient.search(s -> s
                        .index(INDEX_NAME)
                        .query(q -> q.matchAll(m -> m)),
                Article.class
        );

        return response.hits().hits().stream()
                .map(Hit::source)
                .collect(Collectors.toList());
    }
}
