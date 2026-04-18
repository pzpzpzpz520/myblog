package com.ruoyi.blogadmin.domain;

import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 分类查询条件
 */
public class BlogCategoryQuery extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private String name;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
