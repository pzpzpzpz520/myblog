package com.zzq.blog.api.model.vo;

import lombok.Data;

import java.util.List;

@Data
public class HomeVO {

    private UserProfileVO profile;
    private List<ArticleSummaryVO> latestArticles;
    private List<CategoryVO> categories;
    private List<TagVO> tags;
    private Integer articleCount;
    private List<NavigationItemVO> navigation;
    private FooterInfoVO footer;
    private List<SiteModuleVO> homeSections;
    private List<SiteModuleVO> aboutSections;
}
