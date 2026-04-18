package com.ruoyi.blogadmin.domain;

import java.time.LocalDate;
import java.util.List;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 博客文章
 */
public class BlogArticle extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long id;

    private String title;

    private String summary;

    private String contentMarkdown;

    private String coverImage;

    private Long categoryId;

    private String categoryName;

    private LocalDate publishDate;

    private Integer readingTime;

    /**
     * 1 已发布 0 下线
     */
    private Integer status;

    /**
     * 标签id集合（编辑时使用）
     */
    private List<Long> tagIds;

    /**
     * 标签名称集合（列表展示使用）
     */
    private List<String> tags;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getSummary()
    {
        return summary;
    }

    public void setSummary(String summary)
    {
        this.summary = summary;
    }

    public String getContentMarkdown()
    {
        return contentMarkdown;
    }

    public void setContentMarkdown(String contentMarkdown)
    {
        this.contentMarkdown = contentMarkdown;
    }

    public String getCoverImage()
    {
        return coverImage;
    }

    public void setCoverImage(String coverImage)
    {
        this.coverImage = coverImage;
    }

    public Long getCategoryId()
    {
        return categoryId;
    }

    public void setCategoryId(Long categoryId)
    {
        this.categoryId = categoryId;
    }

    public String getCategoryName()
    {
        return categoryName;
    }

    public void setCategoryName(String categoryName)
    {
        this.categoryName = categoryName;
    }

    public LocalDate getPublishDate()
    {
        return publishDate;
    }

    public void setPublishDate(LocalDate publishDate)
    {
        this.publishDate = publishDate;
    }

    public Integer getReadingTime()
    {
        return readingTime;
    }

    public void setReadingTime(Integer readingTime)
    {
        this.readingTime = readingTime;
    }

    public Integer getStatus()
    {
        return status;
    }

    public void setStatus(Integer status)
    {
        this.status = status;
    }

    public List<Long> getTagIds()
    {
        return tagIds;
    }

    public void setTagIds(List<Long> tagIds)
    {
        this.tagIds = tagIds;
    }

    public List<String> getTags()
    {
        return tags;
    }

    public void setTags(List<String> tags)
    {
        this.tags = tags;
    }
}
