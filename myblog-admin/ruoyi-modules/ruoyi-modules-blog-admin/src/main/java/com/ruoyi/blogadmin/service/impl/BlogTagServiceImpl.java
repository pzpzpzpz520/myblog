package com.ruoyi.blogadmin.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.blogadmin.domain.BlogTag;
import com.ruoyi.blogadmin.domain.BlogTagQuery;
import com.ruoyi.blogadmin.mapper.BlogTagMapper;
import com.ruoyi.blogadmin.service.IBlogTagService;
import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.common.core.utils.StringUtils;

/**
 * 标签服务实现
 */
@Service
public class BlogTagServiceImpl implements IBlogTagService
{
    @Autowired
    private BlogTagMapper tagMapper;

    @Override
    public List<BlogTag> selectTagList(BlogTagQuery query)
    {
        return tagMapper.selectTagList(query);
    }

    @Override
    public BlogTag selectTagById(Long tagId)
    {
        return tagMapper.selectTagById(tagId);
    }

    @Override
    public int insertTag(BlogTag tag)
    {
        validateTag(tag, true);
        return tagMapper.insertTag(tag);
    }

    @Override
    public int updateTag(BlogTag tag)
    {
        validateTag(tag, false);
        BlogTag existed = tagMapper.selectTagById(tag.getId());
        if (StringUtils.isNull(existed))
        {
            throw new ServiceException("标签不存在");
        }
        return tagMapper.updateTag(tag);
    }

    @Override
    public int deleteTagById(Long tagId)
    {
        if (StringUtils.isNull(tagId))
        {
            throw new ServiceException("标签ID不能为空");
        }
        BlogTag existed = tagMapper.selectTagById(tagId);
        if (StringUtils.isNull(existed))
        {
            throw new ServiceException("标签不存在");
        }
        int refCount = tagMapper.countArticleByTagId(tagId);
        if (refCount > 0)
        {
            throw new ServiceException("该标签已被文章引用，无法删除");
        }
        return tagMapper.deleteTagById(tagId);
    }

    private void validateTag(BlogTag tag, boolean create)
    {
        if (StringUtils.isNull(tag))
        {
            throw new ServiceException("标签参数不能为空");
        }
        if (!create && StringUtils.isNull(tag.getId()))
        {
            throw new ServiceException("标签ID不能为空");
        }
        if (StringUtils.isEmpty(tag.getName()))
        {
            throw new ServiceException("标签名称不能为空");
        }
        BlogTag duplicated = tagMapper.checkTagNameUnique(tag.getName(), tag.getId());
        if (StringUtils.isNotNull(duplicated))
        {
            throw new ServiceException("标签名称已存在");
        }
    }
}
