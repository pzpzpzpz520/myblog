package com.ruoyi.blogadmin.service;

import java.util.List;
import com.ruoyi.blogadmin.domain.BlogCategory;
import com.ruoyi.blogadmin.domain.BlogCategoryQuery;

/**
 * 分类服务接口
 */
public interface IBlogCategoryService
{
    List<BlogCategory> selectCategoryList(BlogCategoryQuery query);

    BlogCategory selectCategoryById(Long categoryId);

    int insertCategory(BlogCategory category);

    int updateCategory(BlogCategory category);

    int deleteCategoryById(Long categoryId);
}
