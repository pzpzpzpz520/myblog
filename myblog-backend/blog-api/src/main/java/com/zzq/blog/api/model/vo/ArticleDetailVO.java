package com.zzq.blog.api.model.vo;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ArticleDetailVO {

    private Long id;
    private String title;
    private String summary;
    private String content;
    private String coverImage;
    private String categoryName;
    private List<String> tags;
    private LocalDate publishDate;
    private Integer readingTime;
    private Long previousArticleId;
    private String previousArticleTitle;
    private Long nextArticleId;
    private String nextArticleTitle;
}
