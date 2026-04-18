package com.ruoyi.blogadmin.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.ruoyi.blogadmin.domain.BlogCategory;
import com.ruoyi.blogadmin.domain.BlogCategoryQuery;

/**
 * 分类Mapper
 */
public interface BlogCategoryMapper
{
    List<BlogCategory> selectCategoryList(BlogCategoryQuery query);

    BlogCategory selectCategoryById(Long categoryId);

    BlogCategory checkCategoryNameUnique(@Param("name") String name, @Param("excludeId") Long excludeId);

    int insertCategory(BlogCategory category);

    int updateCategory(BlogCategory category);

    int deleteCategoryById(Long categoryId);

    int countArticleByCategoryId(Long categoryId);
}
