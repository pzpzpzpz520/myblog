package com.zzq.blog.content.controller.admin;

import com.zzq.blog.api.model.dto.ArticleQueryDTO;
import com.zzq.blog.api.model.vo.admin.AdminReservationVO;
import com.zzq.blog.common.result.Result;
import com.zzq.blog.content.service.ContentQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/articles")
@RequiredArgsConstructor
public class AdminArticleController {

    private final ContentQueryService contentQueryService;

    @GetMapping
    public Result<AdminReservationVO<Map<String, Object>>> listArticles() {
        ArticleQueryDTO queryDTO = new ArticleQueryDTO();
        queryDTO.setPageNum(1L);
        queryDTO.setPageSize(5L);

        return Result.success(AdminReservationVO.reserved(
                "articles",
                "后台文章管理接口已预留，可在后续管理系统接入时补充鉴权、编辑、发布与版本能力。",
                List.of("list", "create", "update", "publish", "archive"),
                Map.of(
                        "preview", contentQueryService.getArticlePage(queryDTO),
                        "entrypoint", "/api/admin/articles"
                )
        ));
    }
}
