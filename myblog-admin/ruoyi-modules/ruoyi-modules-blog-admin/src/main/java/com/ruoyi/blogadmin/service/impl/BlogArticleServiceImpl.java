package com.ruoyi.blogadmin.service.impl;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.blogadmin.domain.BlogArticle;
import com.ruoyi.blogadmin.domain.BlogArticleQuery;
import com.ruoyi.blogadmin.mapper.BlogArticleMapper;
import com.ruoyi.blogadmin.service.IBlogArticleService;
import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.common.core.utils.StringUtils;

/**
 * 文章服务实现
 */
@Service
public class BlogArticleServiceImpl implements IBlogArticleService
{
    @Autowired
    private BlogArticleMapper articleMapper;

    @Override
    public List<BlogArticle> selectArticleList(BlogArticleQuery query)
    {
        List<BlogArticle> list = articleMapper.selectArticleList(query);
        for (BlogArticle article : list)
        {
            fillArticleTags(article);
        }
        return list;
    }

    @Override
    public BlogArticle selectArticleById(Long articleId)
    {
        BlogArticle article = articleMapper.selectArticleById(articleId);
        if (StringUtils.isNull(article))
        {
            return null;
        }
        fillArticleTags(article);
        return article;
    }

    @Override
    public int insertArticle(BlogArticle article)
    {
        validateArticle(article, true);
        applyDefaults(article);
        int rows = articleMapper.insertArticle(article);
        if (rows > 0)
        {
            syncArticleTags(article.getId(), article.getTagIds());
        }
        return rows;
    }

    @Override
    public int updateArticle(BlogArticle article)
    {
        validateArticle(article, false);
        BlogArticle existed = articleMapper.selectArticleById(article.getId());
        if (StringUtils.isNull(existed))
        {
            throw new ServiceException("文章不存在");
        }
        applyDefaults(article);
        int rows = articleMapper.updateArticle(article);
        if (rows > 0)
        {
            syncArticleTags(article.getId(), article.getTagIds());
        }
        return rows;
    }

    @Override
    public int updateArticleStatus(Long articleId, Integer status)
    {
        if (StringUtils.isNull(articleId))
        {
            throw new ServiceException("文章ID不能为空");
        }
        if (!Objects.equals(status, 0) && !Objects.equals(status, 1))
        {
            throw new ServiceException("状态值非法，仅支持 0(下线) 或 1(发布)");
        }
        BlogArticle existed = articleMapper.selectArticleById(articleId);
        if (StringUtils.isNull(existed))
        {
            throw new ServiceException("文章不存在");
        }
        return articleMapper.updateArticleStatus(articleId, status);
    }

    @Override
    public int deleteArticleByIds(Long[] articleIds)
    {
        if (StringUtils.isEmpty(articleIds))
        {
            return 0;
        }
        for (Long articleId : articleIds)
        {
            articleMapper.deleteArticleTagsByArticleId(articleId);
        }
        return articleMapper.deleteArticleByIds(articleIds);
    }

    private void fillArticleTags(BlogArticle article)
    {
        article.setTagIds(articleMapper.selectTagIdsByArticleId(article.getId()));
        article.setTags(articleMapper.selectTagNamesByArticleId(article.getId()));
    }

    private void syncArticleTags(Long articleId, List<Long> tagIds)
    {
        articleMapper.deleteArticleTagsByArticleId(articleId);
        List<Long> cleanTagIds = normalizeTagIds(tagIds);
        if (StringUtils.isNotEmpty(cleanTagIds))
        {
            articleMapper.insertArticleTags(articleId, cleanTagIds);
        }
    }

    private List<Long> normalizeTagIds(List<Long> tagIds)
    {
        if (StringUtils.isEmpty(tagIds))
        {
            return Collections.emptyList();
        }
        return tagIds.stream().filter(Objects::nonNull).distinct().collect(Collectors.toList());
    }

    private void validateArticle(BlogArticle article, boolean create)
    {
        if (StringUtils.isNull(article))
        {
            throw new ServiceException("文章参数不能为空");
        }
        if (!create && StringUtils.isNull(article.getId()))
        {
            throw new ServiceException("文章ID不能为空");
        }
        if (StringUtils.isEmpty(article.getTitle()))
        {
            throw new ServiceException("文章标题不能为空");
        }
        if (StringUtils.isEmpty(article.getSummary()))
        {
            throw new ServiceException("文章摘要不能为空");
        }
        if (StringUtils.isEmpty(article.getContentMarkdown()))
        {
            throw new ServiceException("文章正文不能为空");
        }
        if (StringUtils.isNull(article.getCategoryId()))
        {
            throw new ServiceException("文章分类不能为空");
        }
        if (StringUtils.isNotNull(article.getStatus()) && !Objects.equals(article.getStatus(), 0) && !Objects.equals(article.getStatus(), 1))
        {
            throw new ServiceException("状态值非法，仅支持 0(下线) 或 1(发布)");
        }
    }

    private void applyDefaults(BlogArticle article)
    {
        if (StringUtils.isNull(article.getPublishDate()))
        {
            article.setPublishDate(LocalDate.now());
        }
        if (StringUtils.isNull(article.getReadingTime()) || article.getReadingTime() <= 0)
        {
            article.setReadingTime(5);
        }
        if (StringUtils.isNull(article.getStatus()))
        {
            article.setStatus(0);
        }
    }
}
