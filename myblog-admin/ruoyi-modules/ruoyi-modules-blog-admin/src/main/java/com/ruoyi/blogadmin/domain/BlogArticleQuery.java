package com.ruoyi.blogadmin.domain;

import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 文章查询条件
 */
public class BlogArticleQuery extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private String keyword;

    private Long categoryId;

    private Integer status;

    public String getKeyword()
    {
        return keyword;
    }

    public void setKeyword(String keyword)
    {
        this.keyword = keyword;
    }

    public Long getCategoryId()
    {
        return categoryId;
    }

    public void setCategoryId(Long categoryId)
    {
        this.categoryId = categoryId;
    }

    public Integer getStatus()
    {
        return status;
    }

    public void setStatus(Integer status)
    {
        this.status = status;
    }
}
