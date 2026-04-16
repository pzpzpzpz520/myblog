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
@RequestMapping("/api/admin/categories")
@RequiredArgsConstructor
public class AdminCategoryController {

    private final ContentQueryService contentQueryService;

    @GetMapping
    public Result<AdminReservationVO<Map<String, Object>>> listCategories() {
        return Result.success(AdminReservationVO.reserved(
                "categories",
                "后台分类管理接口已预留，可在后续管理系统接入时扩展排序、启停和统计能力。",
                List.of("list", "create", "update", "delete"),
                Map.of(
                        "preview", contentQueryService.getCategories(),
                        "entrypoint", "/api/admin/categories"
                )
        ));
    }
}
