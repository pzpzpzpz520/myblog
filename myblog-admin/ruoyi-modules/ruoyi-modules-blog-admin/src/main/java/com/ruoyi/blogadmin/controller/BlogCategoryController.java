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
import com.ruoyi.blogadmin.domain.BlogCategory;
import com.ruoyi.blogadmin.domain.BlogCategoryQuery;
import com.ruoyi.blogadmin.service.IBlogCategoryService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;

/**
 * 分类管理
 */
@RestController
@RequestMapping("/categories")
public class BlogCategoryController extends BaseController
{
    @Autowired
    private IBlogCategoryService categoryService;

    /**
     * 分类列表
     */
    @RequiresPermissions("blog:category:list")
    @GetMapping
    public TableDataInfo list(BlogCategoryQuery query)
    {
        startPage();
        List<BlogCategory> list = categoryService.selectCategoryList(query);
        return getDataTable(list);
    }

    /**
     * 分类详情
     */
    @RequiresPermissions("blog:category:query")
    @GetMapping("/{categoryId}")
    public AjaxResult getInfo(@PathVariable Long categoryId)
    {
        return success(categoryService.selectCategoryById(categoryId));
    }

    /**
     * 新增分类
     */
    @RequiresPermissions("blog:category:add")
    @Log(title = "分类管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BlogCategory category)
    {
        return toAjax(categoryService.insertCategory(category));
    }

    /**
     * 修改分类
     */
    @RequiresPermissions("blog:category:edit")
    @Log(title = "分类管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BlogCategory category)
    {
        return toAjax(categoryService.updateCategory(category));
    }

    /**
     * 删除分类
     */
    @RequiresPermissions("blog:category:remove")
    @Log(title = "分类管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{categoryId}")
    public AjaxResult remove(@PathVariable Long categoryId)
    {
        return toAjax(categoryService.deleteCategoryById(categoryId));
    }
}
