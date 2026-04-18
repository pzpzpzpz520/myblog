package com.ruoyi.blogadmin.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.blogadmin.domain.BlogTag;
import com.ruoyi.blogadmin.domain.BlogTagQuery;
import com.ruoyi.blogadmin.service.IBlogTagService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;

/**
 * 标签管理
 */
@RestController
@RequestMapping("/tags")
public class BlogTagController extends BaseController
{
    @Autowired
    private IBlogTagService tagService;

    /**
     * 标签列表
     */
    @RequiresPermissions("blog:tag:list")
    @GetMapping
    public TableDataInfo list(BlogTagQuery query)
    {
        startPage();
        List<BlogTag> list = tagService.selectTagList(query);
        return getDataTable(list);
    }

    /**
     * 标签详情
     */
    @RequiresPermissions("blog:tag:query")
    @GetMapping("/{tagId}")
    public AjaxResult getInfo(@PathVariable Long tagId)
    {
        return success(tagService.selectTagById(tagId));
    }

    /**
     * 新增标签
     */
    @RequiresPermissions("blog:tag:add")
    @Log(title = "标签管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BlogTag tag)
    {
        return toAjax(tagService.insertTag(tag));
    }

    /**
     * 修改标签
     */
    @RequiresPermissions("blog:tag:edit")
    @Log(title = "标签管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BlogTag tag)
    {
        return toAjax(tagService.updateTag(tag));
    }

    /**
     * 删除标签
     */
    @RequiresPermissions("blog:tag:remove")
    @Log(title = "标签管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{tagId}")
    public AjaxResult remove(@PathVariable Long tagId)
    {
        return toAjax(tagService.deleteTagById(tagId));
    }
}
