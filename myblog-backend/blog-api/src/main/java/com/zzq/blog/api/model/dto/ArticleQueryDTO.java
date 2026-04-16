package com.zzq.blog.api.model.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class ArticleQueryDTO {

    @Min(value = 1, message = "pageNum must be greater than 0")
    private Long pageNum = 1L;

    @Min(value = 1, message = "pageSize must be greater than 0")
    @Max(value = 50, message = "pageSize must be less than or equal to 50")
    private Long pageSize = 6L;

    private Long categoryId;
    private Long tagId;
    private String keyword;
}
