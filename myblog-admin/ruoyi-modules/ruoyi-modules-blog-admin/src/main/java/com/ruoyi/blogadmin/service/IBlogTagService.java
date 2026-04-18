package com.ruoyi.blogadmin.service;

import java.util.List;
import com.ruoyi.blogadmin.domain.BlogTag;
import com.ruoyi.blogadmin.domain.BlogTagQuery;

/**
 * 标签服务接口
 */
public interface IBlogTagService
{
    List<BlogTag> selectTagList(BlogTagQuery query);

    BlogTag selectTagById(Long tagId);

    int insertTag(BlogTag tag);

    int updateTag(BlogTag tag);

    int deleteTagById(Long tagId);
}
