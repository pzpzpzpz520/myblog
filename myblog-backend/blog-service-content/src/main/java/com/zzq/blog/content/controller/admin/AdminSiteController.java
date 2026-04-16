package com.zzq.blog.content.controller.admin;

import com.zzq.blog.api.model.vo.FooterInfoVO;
import com.zzq.blog.api.model.vo.NavigationItemVO;
import com.zzq.blog.api.model.vo.SiteModuleVO;
import com.zzq.blog.api.model.vo.UserProfileVO;
import com.zzq.blog.api.model.vo.admin.AdminReservationVO;
import com.zzq.blog.common.result.Result;
import com.zzq.blog.content.service.ContentQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/site")
@RequiredArgsConstructor
public class AdminSiteController {

    private final ContentQueryService contentQueryService;

    @GetMapping("/profile")
    public Result<AdminReservationVO<UserProfileVO>> getProfile() {
        return Result.success(AdminReservationVO.reserved(
                "site-profile",
                "后台个人资料管理接口已预留，可在后续管理系统中接入表单编辑与发布流程。",
                List.of("view", "update"),
                contentQueryService.getProfile()
        ));
    }

    @GetMapping("/modules")
    public Result<AdminReservationVO<List<SiteModuleVO>>> getModules() {
        return Result.success(AdminReservationVO.reserved(
                "site-modules",
                "后台站点模块接口已预留，可在后续管理系统中扩展模块开关、排序和内容编排能力。",
                List.of("list", "update", "sort", "toggle"),
                contentQueryService.getSiteModules(null)
        ));
    }

    @GetMapping("/navigation")
    public Result<AdminReservationVO<List<NavigationItemVO>>> getNavigation() {
        return Result.success(AdminReservationVO.reserved(
                "site-navigation",
                "后台导航配置接口已预留，可在后续管理系统中扩展菜单编排与外链配置能力。",
                List.of("list", "update", "sort"),
                contentQueryService.getNavigation()
        ));
    }

    @GetMapping("/footer")
    public Result<AdminReservationVO<FooterInfoVO>> getFooter() {
        return Result.success(AdminReservationVO.reserved(
                "site-footer",
                "后台页脚配置接口已预留，可在后续管理系统中扩展站点说明、联系信息和链接管理能力。",
                List.of("view", "update"),
                contentQueryService.getFooter()
        ));
    }
}
