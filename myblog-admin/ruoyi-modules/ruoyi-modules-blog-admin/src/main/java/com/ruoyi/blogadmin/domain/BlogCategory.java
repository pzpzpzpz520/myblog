package com.ruoyi.blogadmin.domain;

import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 分类
 */
public class BlogCategory extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private Long articleCount;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Long getArticleCount()
    {
        return articleCount;
    }

    public void setArticleCount(Long articleCount)
    {
        this.articleCount = articleCount;
    }
}
