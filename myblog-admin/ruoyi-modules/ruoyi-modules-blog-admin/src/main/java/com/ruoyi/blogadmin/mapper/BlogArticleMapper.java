package com.ruoyi.blogadmin.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.ruoyi.blogadmin.domain.BlogArticle;
import com.ruoyi.blogadmin.domain.BlogArticleQuery;

/**
 * 文章Mapper
 */
public interface BlogArticleMapper
{
    List<BlogArticle> selectArticleList(BlogArticleQuery query);

    BlogArticle selectArticleById(Long articleId);

    int insertArticle(BlogArticle article);

    int updateArticle(BlogArticle article);

    int updateArticleStatus(@Param("articleId") Long articleId, @Param("status") Integer status);

    int deleteArticleByIds(@Param("articleIds") Long[] articleIds);

    List<Long> selectTagIdsByArticleId(Long articleId);

    List<String> selectTagNamesByArticleId(Long articleId);

    int deleteArticleTagsByArticleId(Long articleId);

    int insertArticleTags(@Param("articleId") Long articleId, @Param("tagIds") List<Long> tagIds);
}
