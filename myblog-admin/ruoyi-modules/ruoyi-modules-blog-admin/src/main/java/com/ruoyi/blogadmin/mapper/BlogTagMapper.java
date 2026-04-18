package com.ruoyi.blogadmin.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.ruoyi.blogadmin.domain.BlogTag;
import com.ruoyi.blogadmin.domain.BlogTagQuery;

/**
 * 标签Mapper
 */
public interface BlogTagMapper
{
    List<BlogTag> selectTagList(BlogTagQuery query);

    BlogTag selectTagById(Long tagId);

    BlogTag checkTagNameUnique(@Param("name") String name, @Param("excludeId") Long excludeId);

    int insertTag(BlogTag tag);

    int updateTag(BlogTag tag);

    int deleteTagById(Long tagId);

    int countArticleByTagId(Long tagId);
}
