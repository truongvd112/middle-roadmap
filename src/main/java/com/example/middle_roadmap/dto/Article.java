package com.example.middle_roadmap.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Article {
    private String title;
    private String content;
    private String author;
    private Long viewCnt;
    private LocalDateTime createdAt;
    private long id;
}
