package com.zzq.blog.content.controller;

import com.zzq.blog.api.model.vo.CategoryVO;
import com.zzq.blog.api.model.vo.TagVO;
import com.zzq.blog.common.result.Result;
import com.zzq.blog.content.service.ContentQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TaxonomyController {

    private final ContentQueryService contentQueryService;

    @GetMapping("/categories")
    public Result<List<CategoryVO>> getCategories() {
        return Result.success(contentQueryService.getCategories());
    }

    @GetMapping("/tags")
    public Result<List<TagVO>> getTags() {
        return Result.success(contentQueryService.getTags());
    }
}
