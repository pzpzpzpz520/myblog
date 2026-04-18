package com.ruoyi.blogadmin.mapper;

import com.ruoyi.blogadmin.domain.BlogProfile;

/**
 * 站点资料Mapper
 */
public interface BlogProfileMapper
{
    BlogProfile selectProfile();

    int insertProfile(BlogProfile profile);

    int updateProfile(BlogProfile profile);
}
