package com.ruoyi.blogadmin.service;

import com.ruoyi.blogadmin.domain.BlogProfile;

/**
 * 站点资料服务接口
 */
public interface IBlogProfileService
{
    BlogProfile getProfile();

    int updateProfile(BlogProfile profile);
}
