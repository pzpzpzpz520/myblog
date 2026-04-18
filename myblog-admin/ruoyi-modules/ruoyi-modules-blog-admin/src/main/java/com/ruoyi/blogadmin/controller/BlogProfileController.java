package com.ruoyi.blogadmin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.blogadmin.domain.BlogProfile;
import com.ruoyi.blogadmin.service.IBlogProfileService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;

/**
 * 站点资料管理
 */
@RestController
@RequestMapping("/site/profile")
public class BlogProfileController extends BaseController
{
    @Autowired
    private IBlogProfileService profileService;

    /**
     * 获取站点资料
     */
    @RequiresPermissions("blog:profile:query")
    @GetMapping
    public AjaxResult getInfo()
    {
        return success(profileService.getProfile());
    }

    /**
     * 更新站点资料
     */
    @RequiresPermissions("blog:profile:edit")
    @Log(title = "站点资料", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BlogProfile profile)
    {
        return toAjax(profileService.updateProfile(profile));
    }
}
