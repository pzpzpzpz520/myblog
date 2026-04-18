package com.ruoyi.blogadmin.domain;

import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 站点资料
 */
public class BlogProfile extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private String title;

    private String university;

    private String major;

    private String location;

    private String email;

    private String bio;

    /**
     * 逗号分隔
     */
    private String focusAreas;

    /**
     * 逗号分隔
     */
    private String highlights;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getUniversity()
    {
        return university;
    }

    public void setUniversity(String university)
    {
        this.university = university;
    }

    public String getMajor()
    {
        return major;
    }

    public void setMajor(String major)
    {
        this.major = major;
    }

    public String getLocation()
    {
        return location;
    }

    public void setLocation(String location)
    {
        this.location = location;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getBio()
    {
        return bio;
    }

    public void setBio(String bio)
    {
        this.bio = bio;
    }

    public String getFocusAreas()
    {
        return focusAreas;
    }

    public void setFocusAreas(String focusAreas)
    {
        this.focusAreas = focusAreas;
    }

    public String getHighlights()
    {
        return highlights;
    }

    public void setHighlights(String highlights)
    {
        this.highlights = highlights;
    }
}
