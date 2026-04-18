package com.ruoyi.blogadmin.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.blogadmin.domain.BlogCategory;
import com.ruoyi.blogadmin.domain.BlogCategoryQuery;
import com.ruoyi.blogadmin.mapper.BlogCategoryMapper;
import com.ruoyi.blogadmin.service.IBlogCategoryService;
import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.common.core.utils.StringUtils;

/**
 * 分类服务实现
 */
@Service
public class BlogCategoryServiceImpl implements IBlogCategoryService
{
    @Autowired
    private BlogCategoryMapper categoryMapper;

    @Override
    public List<BlogCategory> selectCategoryList(BlogCategoryQuery query)
    {
        return categoryMapper.selectCategoryList(query);
    }

    @Override
    public BlogCategory selectCategoryById(Long categoryId)
    {
        return categoryMapper.selectCategoryById(categoryId);
    }

    @Override
    public int insertCategory(BlogCategory category)
    {
        validateCategory(category, true);
        return categoryMapper.insertCategory(category);
    }

    @Override
    public int updateCategory(BlogCategory category)
    {
        validateCategory(category, false);
        BlogCategory existed = categoryMapper.selectCategoryById(category.getId());
        if (StringUtils.isNull(existed))
        {
            throw new ServiceException("分类不存在");
        }
        return categoryMapper.updateCategory(category);
    }

    @Override
    public int deleteCategoryById(Long categoryId)
    {
        if (StringUtils.isNull(categoryId))
        {
            throw new ServiceException("分类ID不能为空");
        }
        BlogCategory existed = categoryMapper.selectCategoryById(categoryId);
        if (StringUtils.isNull(existed))
        {
            throw new ServiceException("分类不存在");
        }
        int refCount = categoryMapper.countArticleByCategoryId(categoryId);
        if (refCount > 0)
        {
            throw new ServiceException("该分类已被文章引用，无法删除");
        }
        return categoryMapper.deleteCategoryById(categoryId);
    }

    private void validateCategory(BlogCategory category, boolean create)
    {
        if (StringUtils.isNull(category))
        {
            throw new ServiceException("分类参数不能为空");
        }
        if (!create && StringUtils.isNull(category.getId()))
        {
            throw new ServiceException("分类ID不能为空");
        }
        if (StringUtils.isEmpty(category.getName()))
        {
            throw new ServiceException("分类名称不能为空");
        }
        BlogCategory duplicated = categoryMapper.checkCategoryNameUnique(category.getName(), category.getId());
        if (StringUtils.isNotNull(duplicated))
        {
            throw new ServiceException("分类名称已存在");
        }
    }
}
