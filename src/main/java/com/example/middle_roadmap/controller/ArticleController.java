package com.example.middle_roadmap.controller;

import com.example.middle_roadmap.dto.Article;
import com.example.middle_roadmap.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/articles")
@RequiredArgsConstructor
@CrossOrigin
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody Article article) throws IOException {
        articleService.saveArticle(article);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<Article>> search(@RequestParam String keyword) throws IOException {
        return ResponseEntity.ok(articleService.searchByTitle(keyword));
    }

    @GetMapping("/search-advanced")
    public ResponseEntity<List<Article>> advancedSearch(
            @RequestParam String keyword,
            @RequestParam String fromDate,
            @RequestParam String toDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir
    ) throws IOException {
        return ResponseEntity.ok(articleService.searchWithAdvanced(keyword, fromDate, toDate, page, size, sortBy, sortDir));
    }

    @GetMapping("/search-title-date")
    public ResponseEntity<List<Article>> searchByTitleAndDate(
            @RequestParam String keyword,
            @RequestParam String fromDate,
            @RequestParam String toDate
    ) throws IOException {
        return ResponseEntity.ok(articleService.searchByTitleAndDate(keyword, fromDate, toDate));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody Map<String, String> updateMap) throws IOException {
        articleService.updateArticle(id, updateMap.get("title"));
        return ResponseEntity.ok("Updated");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) throws IOException {
        articleService.deleteArticle(id);
        return ResponseEntity.ok("Deleted");
    }

    @DeleteMapping("/index")
    public ResponseEntity<?> deleteIndex() throws IOException {
        articleService.deleteIndex();
        return ResponseEntity.ok("Index deleted");
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count(@RequestParam String keyword) throws IOException {
        return ResponseEntity.ok(articleService.countArticlesByTitle(keyword));
    }

    @GetMapping
    public ResponseEntity<List<Article>> findAll() throws IOException {
        return ResponseEntity.ok(articleService.findAll());
    }
}
