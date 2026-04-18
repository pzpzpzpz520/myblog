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
import com.ruoyi.blogadmin.domain.BlogArticle;
import com.ruoyi.blogadmin.domain.BlogArticleQuery;
import com.ruoyi.blogadmin.domain.BlogPublishRequest;
import com.ruoyi.blogadmin.service.IBlogArticleService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;

/**
 * 文章管理
 */
@RestController
@RequestMapping("/articles")
public class BlogArticleController extends BaseController
{
    @Autowired
    private IBlogArticleService articleService;

    /**
     * 文章列表
     */
    @RequiresPermissions("blog:article:list")
    @GetMapping
    public TableDataInfo list(BlogArticleQuery query)
    {
        startPage();
        List<BlogArticle> list = articleService.selectArticleList(query);
        return getDataTable(list);
    }

    /**
     * 文章详情
     */
    @RequiresPermissions("blog:article:query")
    @GetMapping("/{articleId}")
    public AjaxResult getInfo(@PathVariable Long articleId)
    {
        return success(articleService.selectArticleById(articleId));
    }

    /**
     * 新增文章
     */
    @RequiresPermissions("blog:article:add")
    @Log(title = "文章管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BlogArticle article)
    {
        return toAjax(articleService.insertArticle(article));
    }

    /**
     * 修改文章
     */
    @RequiresPermissions("blog:article:edit")
    @Log(title = "文章管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BlogArticle article)
    {
        return toAjax(articleService.updateArticle(article));
    }

    /**
     * 发布/下线
     */
    @RequiresPermissions("blog:article:publish")
    @Log(title = "文章发布状态", businessType = BusinessType.UPDATE)
    @PutMapping("/{articleId}/publish")
    public AjaxResult publish(@PathVariable Long articleId, @RequestBody BlogPublishRequest request)
    {
        Integer status = request == null ? null : request.getStatus();
        return toAjax(articleService.updateArticleStatus(articleId, status));
    }

    /**
     * 删除文章
     */
    @RequiresPermissions("blog:article:remove")
    @Log(title = "文章管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{articleIds}")
    public AjaxResult remove(@PathVariable Long[] articleIds)
    {
        return toAjax(articleService.deleteArticleByIds(articleIds));
    }
}
