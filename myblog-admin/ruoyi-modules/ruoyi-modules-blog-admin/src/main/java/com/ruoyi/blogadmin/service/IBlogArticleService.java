package com.ruoyi.blogadmin.service;

import java.util.List;
import com.ruoyi.blogadmin.domain.BlogArticle;
import com.ruoyi.blogadmin.domain.BlogArticleQuery;

/**
 * 文章服务接口
 */
public interface IBlogArticleService
{
    List<BlogArticle> selectArticleList(BlogArticleQuery query);

    BlogArticle selectArticleById(Long articleId);

    int insertArticle(BlogArticle article);

    int updateArticle(BlogArticle article);

    int updateArticleStatus(Long articleId, Integer status);

    int deleteArticleByIds(Long[] articleIds);
}
