package com.zzq.blog.content.controller;

import com.zzq.blog.api.model.dto.ArticleQueryDTO;
import com.zzq.blog.api.model.vo.ArticleDetailVO;
import com.zzq.blog.api.model.vo.ArticleSummaryVO;
import com.zzq.blog.common.result.PageResult;
import com.zzq.blog.common.result.Result;
import com.zzq.blog.content.service.ContentQueryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/articles")
@RequiredArgsConstructor
public class ArticleController {

    private final ContentQueryService contentQueryService;

    @GetMapping
    public Result<PageResult<ArticleSummaryVO>> getArticles(@Valid ArticleQueryDTO queryDTO) {
        return Result.success(contentQueryService.getArticlePage(queryDTO));
    }

    @GetMapping("/{articleId}")
    public Result<ArticleDetailVO> getArticleDetail(@PathVariable Long articleId) {
        ArticleDetailVO detailVO = contentQueryService.getArticleDetail(articleId);
        if (detailVO == null) {
            return Result.failure(404, "Article not found");
        }
        return Result.success(detailVO);
    }
}
