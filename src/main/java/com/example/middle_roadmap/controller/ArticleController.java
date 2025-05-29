package com.example.middle_roadmap.controller;

import com.example.middle_roadmap.dto.Article;
import com.example.middle_roadmap.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

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
}
