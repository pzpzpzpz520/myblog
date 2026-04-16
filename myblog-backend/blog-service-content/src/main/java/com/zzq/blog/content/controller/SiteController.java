package com.zzq.blog.content.controller;

import com.zzq.blog.api.model.vo.HomeVO;
import com.zzq.blog.api.model.vo.UserProfileVO;
import com.zzq.blog.common.result.Result;
import com.zzq.blog.content.service.ContentQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/site")
@RequiredArgsConstructor
public class SiteController {

    private final ContentQueryService contentQueryService;

    @GetMapping("/profile")
    public Result<UserProfileVO> getProfile() {
        return Result.success(contentQueryService.getProfile());
    }

    @GetMapping("/home")
    public Result<HomeVO> getHome() {
        return Result.success(contentQueryService.getHome());
    }
}
