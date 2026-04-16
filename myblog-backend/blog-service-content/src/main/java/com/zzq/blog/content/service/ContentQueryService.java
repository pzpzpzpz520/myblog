package com.zzq.blog.content.service;

import com.zzq.blog.api.model.dto.ArticleQueryDTO;
import com.zzq.blog.api.model.vo.ArticleDetailVO;
import com.zzq.blog.api.model.vo.CategoryVO;
import com.zzq.blog.api.model.vo.FooterInfoVO;
import com.zzq.blog.api.model.vo.HomeVO;
import com.zzq.blog.api.model.vo.NavigationItemVO;
import com.zzq.blog.api.model.vo.SiteModuleVO;
import com.zzq.blog.api.model.vo.TagVO;
import com.zzq.blog.api.model.vo.UserProfileVO;
import com.zzq.blog.common.result.PageResult;

import java.util.List;

public interface ContentQueryService {

    UserProfileVO getProfile();

    HomeVO getHome();

    PageResult<com.zzq.blog.api.model.vo.ArticleSummaryVO> getArticlePage(ArticleQueryDTO queryDTO);

    ArticleDetailVO getArticleDetail(Long articleId);

    List<CategoryVO> getCategories();

    List<TagVO> getTags();

    List<SiteModuleVO> getSiteModules(String page);

    List<NavigationItemVO> getNavigation();

    FooterInfoVO getFooter();
}
