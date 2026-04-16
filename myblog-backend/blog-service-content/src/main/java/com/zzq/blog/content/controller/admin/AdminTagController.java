package com.zzq.blog.content.controller.admin;

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
@RequestMapping("/api/admin/tags")
@RequiredArgsConstructor
public class AdminTagController {

    private final ContentQueryService contentQueryService;

    @GetMapping
    public Result<AdminReservationVO<Map<String, Object>>> listTags() {
        return Result.success(AdminReservationVO.reserved(
                "tags",
                "后台标签管理接口已预留，可在后续管理系统接入时扩展聚类、推荐和批量维护能力。",
                List.of("list", "create", "update", "delete"),
                Map.of(
                        "preview", contentQueryService.getTags(),
                        "entrypoint", "/api/admin/tags"
                )
        ));
    }
}
