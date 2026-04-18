package com.ruoyi.blogadmin.domain;

import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 标签查询条件
 */
public class BlogTagQuery extends BaseEntity
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
