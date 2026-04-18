package com.ruoyi.blogadmin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.blogadmin.domain.BlogProfile;
import com.ruoyi.blogadmin.mapper.BlogProfileMapper;
import com.ruoyi.blogadmin.service.IBlogProfileService;
import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.common.core.utils.StringUtils;

/**
 * 站点资料服务实现
 */
@Service
public class BlogProfileServiceImpl implements IBlogProfileService
{
    @Autowired
    private BlogProfileMapper profileMapper;

    @Override
    public BlogProfile getProfile()
    {
        BlogProfile profile = profileMapper.selectProfile();
        if (StringUtils.isNull(profile))
        {
            BlogProfile empty = new BlogProfile();
            empty.setId(1L);
            empty.setName("");
            empty.setTitle("");
            empty.setUniversity("");
            empty.setMajor("");
            empty.setLocation("");
            empty.setEmail("");
            empty.setBio("");
            empty.setFocusAreas("");
            empty.setHighlights("");
            return empty;
        }
        return profile;
    }

    @Override
    public int updateProfile(BlogProfile profile)
    {
        if (StringUtils.isNull(profile))
        {
            throw new ServiceException("站点资料参数不能为空");
        }
        if (StringUtils.isNull(profile.getId()))
        {
            profile.setId(1L);
        }
        BlogProfile existed = profileMapper.selectProfile();
        if (StringUtils.isNull(existed))
        {
            return profileMapper.insertProfile(profile);
        }
        profile.setId(existed.getId());
        return profileMapper.updateProfile(profile);
    }
}
