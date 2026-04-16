package com.zzq.blog.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zzq.blog.api.model.dto.ArticleQueryDTO;
import com.zzq.blog.api.model.vo.ArticleDetailVO;
import com.zzq.blog.api.model.vo.ArticleSummaryVO;
import com.zzq.blog.api.model.vo.CategoryVO;
import com.zzq.blog.api.model.vo.FooterInfoVO;
import com.zzq.blog.api.model.vo.HomeVO;
import com.zzq.blog.api.model.vo.NavigationItemVO;
import com.zzq.blog.api.model.vo.SiteModuleVO;
import com.zzq.blog.api.model.vo.TagVO;
import com.zzq.blog.api.model.vo.UserProfileVO;
import com.zzq.blog.common.result.PageResult;
import com.zzq.blog.content.entity.Article;
import com.zzq.blog.content.entity.ArticleTag;
import com.zzq.blog.content.entity.Category;
import com.zzq.blog.content.entity.SiteModule;
import com.zzq.blog.content.entity.Tag;
import com.zzq.blog.content.entity.UserProfile;
import com.zzq.blog.content.mapper.ArticleMapper;
import com.zzq.blog.content.mapper.ArticleTagMapper;
import com.zzq.blog.content.mapper.CategoryMapper;
import com.zzq.blog.content.mapper.SiteModuleMapper;
import com.zzq.blog.content.mapper.TagMapper;
import com.zzq.blog.content.mapper.UserProfileMapper;
import com.zzq.blog.content.service.ContentQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContentQueryServiceImpl implements ContentQueryService {

    private static final TypeReference<Map<String, Object>> MAP_TYPE = new TypeReference<>() {
    };

    private final UserProfileMapper userProfileMapper;
    private final ArticleMapper articleMapper;
    private final CategoryMapper categoryMapper;
    private final TagMapper tagMapper;
    private final ArticleTagMapper articleTagMapper;
    private final SiteModuleMapper siteModuleMapper;
    private final ObjectMapper objectMapper;

    @Override
    public UserProfileVO getProfile() {
        UserProfile userProfile = userProfileMapper.selectById(1L);
        if (userProfile == null) {
            return defaultProfile();
        }
        UserProfileVO vo = new UserProfileVO();
        vo.setName(userProfile.getName());
        vo.setTitle(userProfile.getTitle());
        vo.setUniversity(userProfile.getUniversity());
        vo.setMajor(userProfile.getMajor());
        vo.setLocation(userProfile.getLocation());
        vo.setEmail(userProfile.getEmail());
        vo.setBio(userProfile.getBio());
        vo.setFocusAreas(splitText(userProfile.getFocusAreas()));
        vo.setHighlights(splitText(userProfile.getHighlights()));
        return vo;
    }

    @Override
    public HomeVO getHome() {
        HomeVO homeVO = new HomeVO();
        homeVO.setProfile(getProfile());
        ArticleQueryDTO queryDTO = new ArticleQueryDTO();
        queryDTO.setPageNum(1L);
        queryDTO.setPageSize(3L);
        homeVO.setLatestArticles(getArticlePage(queryDTO).getRecords());
        homeVO.setCategories(getCategories());
        homeVO.setTags(getTags());
        homeVO.setArticleCount(Math.toIntExact(articleMapper.selectCount(publishedOnly())));
        homeVO.setNavigation(getNavigation());
        homeVO.setFooter(getFooter());
        homeVO.setHomeSections(getSiteModules("home").stream()
                .filter(section -> Boolean.TRUE.equals(section.getEnabled()))
                .toList());
        homeVO.setAboutSections(getSiteModules("about").stream()
                .filter(section -> Boolean.TRUE.equals(section.getEnabled()))
                .toList());
        return homeVO;
    }

    @Override
    public PageResult<ArticleSummaryVO> getArticlePage(ArticleQueryDTO queryDTO) {
        List<Long> articleIdsByTag = findArticleIdsByTag(queryDTO.getTagId());
        if (queryDTO.getTagId() != null && articleIdsByTag.isEmpty()) {
            return new PageResult<>(queryDTO.getPageNum(), queryDTO.getPageSize(), 0L, Collections.emptyList());
        }

        LambdaQueryWrapper<Article> wrapper = publishedOnly()
                .orderByDesc(Article::getPublishDate)
                .orderByDesc(Article::getId);

        if (queryDTO.getCategoryId() != null) {
            wrapper.eq(Article::getCategoryId, queryDTO.getCategoryId());
        }
        if (StringUtils.hasText(queryDTO.getKeyword())) {
            wrapper.and(w -> w.like(Article::getTitle, queryDTO.getKeyword())
                    .or()
                    .like(Article::getSummary, queryDTO.getKeyword()));
        }
        if (!articleIdsByTag.isEmpty()) {
            wrapper.in(Article::getId, articleIdsByTag);
        }

        Page<Article> page = articleMapper.selectPage(new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize()), wrapper);
        List<ArticleSummaryVO> records = page.getRecords().stream()
                .map(this::mapSummary)
                .toList();
        return new PageResult<>(page.getCurrent(), page.getSize(), page.getTotal(), records);
    }

    @Override
    public ArticleDetailVO getArticleDetail(Long articleId) {
        Article article = articleMapper.selectById(articleId);
        if (article == null || !Objects.equals(article.getStatus(), 1)) {
            return null;
        }

        ArticleDetailVO detailVO = new ArticleDetailVO();
        ArticleSummaryVO summaryVO = mapSummary(article);
        detailVO.setId(summaryVO.getId());
        detailVO.setTitle(summaryVO.getTitle());
        detailVO.setSummary(summaryVO.getSummary());
        detailVO.setCoverImage(summaryVO.getCoverImage());
        detailVO.setCategoryName(summaryVO.getCategoryName());
        detailVO.setTags(summaryVO.getTags());
        detailVO.setPublishDate(summaryVO.getPublishDate());
        detailVO.setReadingTime(summaryVO.getReadingTime());
        detailVO.setContent(article.getContentMarkdown());

        List<Article> allArticles = articleMapper.selectList(publishedOnly().orderByAsc(Article::getPublishDate).orderByAsc(Article::getId));
        for (int index = 0; index < allArticles.size(); index++) {
            if (Objects.equals(allArticles.get(index).getId(), articleId)) {
                if (index > 0) {
                    detailVO.setPreviousArticleId(allArticles.get(index - 1).getId());
                    detailVO.setPreviousArticleTitle(allArticles.get(index - 1).getTitle());
                }
                if (index < allArticles.size() - 1) {
                    detailVO.setNextArticleId(allArticles.get(index + 1).getId());
                    detailVO.setNextArticleTitle(allArticles.get(index + 1).getTitle());
                }
                break;
            }
        }
        return detailVO;
    }

    @Override
    public List<CategoryVO> getCategories() {
        Map<Long, Long> categoryCountMap = articleMapper.selectList(publishedOnly()).stream()
                .collect(Collectors.groupingBy(Article::getCategoryId, LinkedHashMap::new, Collectors.counting()));
        return categoryMapper.selectList(new LambdaQueryWrapper<Category>().orderByAsc(Category::getId)).stream()
                .map(category -> new CategoryVO(category.getId(), category.getName(), Math.toIntExact(categoryCountMap.getOrDefault(category.getId(), 0L))))
                .toList();
    }

    @Override
    public List<TagVO> getTags() {
        Map<Long, Integer> tagCountMap = new LinkedHashMap<>();
        articleTagMapper.selectList(null).forEach(articleTag -> tagCountMap.merge(articleTag.getTagId(), 1, Integer::sum));
        return tagMapper.selectList(new LambdaQueryWrapper<Tag>().orderByAsc(Tag::getId)).stream()
                .map(tag -> new TagVO(tag.getId(), tag.getName(), tagCountMap.getOrDefault(tag.getId(), 0)))
                .toList();
    }

    @Override
    public List<SiteModuleVO> getSiteModules(String page) {
        return loadSiteModules(page);
    }

    @Override
    public List<NavigationItemVO> getNavigation() {
        SiteModuleVO navigationModule = loadSiteModules("global").stream()
                .filter(module -> "navigation".equals(module.getModuleType()))
                .findFirst()
                .orElse(null);
        if (navigationModule == null) {
            return Collections.emptyList();
        }
        Object items = navigationModule.getPayload().get("items");
        return objectMapper.convertValue(items, new TypeReference<List<NavigationItemVO>>() {
        });
    }

    @Override
    public FooterInfoVO getFooter() {
        SiteModuleVO footerModule = loadSiteModules("global").stream()
                .filter(module -> "footer".equals(module.getModuleType()))
                .findFirst()
                .orElse(null);
        if (footerModule == null) {
            return emptyFooter();
        }
        return objectMapper.convertValue(footerModule.getPayload(), FooterInfoVO.class);
    }

    private List<SiteModuleVO> loadSiteModules(String page) {
        LambdaQueryWrapper<SiteModule> wrapper = new LambdaQueryWrapper<SiteModule>()
                .orderByAsc(SiteModule::getPage)
                .orderByAsc(SiteModule::getSortOrder)
                .orderByAsc(SiteModule::getId);
        if (StringUtils.hasText(page)) {
            wrapper.eq(SiteModule::getPage, page);
        }
        List<SiteModule> modules = siteModuleMapper.selectList(wrapper);
        if (modules.isEmpty()) {
            return Collections.emptyList();
        }
        return modules.stream()
                .map(this::mapSiteModule)
                .toList();
    }

    private SiteModuleVO mapSiteModule(SiteModule module) {
        SiteModuleVO vo = new SiteModuleVO();
        vo.setId(module.getId());
        vo.setPage(module.getPage());
        vo.setKey(module.getModuleKey());
        vo.setTitle(module.getTitle());
        vo.setModuleType(module.getModuleType());
        vo.setEnabled(Objects.equals(module.getEnabled(), 1));
        vo.setSortOrder(module.getSortOrder());
        vo.setPayload(readPayload(module.getPayloadJson()));
        return vo;
    }

    private Map<String, Object> readPayload(String payloadJson) {
        if (!StringUtils.hasText(payloadJson)) {
            return Collections.emptyMap();
        }
        try {
            return objectMapper.readValue(payloadJson, MAP_TYPE);
        } catch (Exception exception) {
            return Map.of("raw", payloadJson);
        }
    }

    private LambdaQueryWrapper<Article> publishedOnly() {
        return new LambdaQueryWrapper<Article>().eq(Article::getStatus, 1);
    }

    private ArticleSummaryVO mapSummary(Article article) {
        ArticleSummaryVO vo = new ArticleSummaryVO();
        vo.setId(article.getId());
        vo.setTitle(article.getTitle());
        vo.setSummary(article.getSummary());
        vo.setCoverImage(article.getCoverImage());
        vo.setPublishDate(article.getPublishDate());
        vo.setReadingTime(article.getReadingTime());
        Category category = categoryMapper.selectById(article.getCategoryId());
        vo.setCategoryName(category == null ? "未分类" : category.getName());
        List<Long> tagIds = articleTagMapper.selectList(new LambdaQueryWrapper<ArticleTag>().eq(ArticleTag::getArticleId, article.getId()))
                .stream()
                .map(ArticleTag::getTagId)
                .toList();
        if (tagIds.isEmpty()) {
            vo.setTags(Collections.emptyList());
        } else {
            vo.setTags(tagMapper.selectBatchIds(tagIds).stream().map(Tag::getName).toList());
        }
        return vo;
    }

    private List<Long> findArticleIdsByTag(Long tagId) {
        if (tagId == null) {
            return Collections.emptyList();
        }
        return articleTagMapper.selectList(new LambdaQueryWrapper<ArticleTag>().eq(ArticleTag::getTagId, tagId))
                .stream()
                .map(ArticleTag::getArticleId)
                .distinct()
                .toList();
    }

    private List<String> splitText(String value) {
        if (!StringUtils.hasText(value)) {
            return Collections.emptyList();
        }
        return List.of(value.split(","));
    }

    private UserProfileVO defaultProfile() {
        UserProfileVO vo = new UserProfileVO();
        vo.setName("");
        vo.setTitle("");
        vo.setUniversity("");
        vo.setMajor("");
        vo.setLocation("");
        vo.setEmail("");
        vo.setBio("");
        vo.setFocusAreas(Collections.emptyList());
        vo.setHighlights(Collections.emptyList());
        return vo;
    }

    private FooterInfoVO emptyFooter() {
        FooterInfoVO footerInfoVO = new FooterInfoVO();
        footerInfoVO.setSlogan("");
        footerInfoVO.setSignature("");
        footerInfoVO.setEmail("");
        footerInfoVO.setLocation("");
        footerInfoVO.setLinks(Collections.emptyList());
        return footerInfoVO;
    }
}
