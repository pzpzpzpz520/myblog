package com.zzq.blog.content.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;

@Data
@TableName("article")
public class Article {

    @TableId(type = IdType.AUTO)
    private Long id;
    private String title;
    private String summary;
    @TableField("content_markdown")
    private String contentMarkdown;
    @TableField("cover_image")
    private String coverImage;
    @TableField("category_id")
    private Long categoryId;
    @TableField("publish_date")
    private LocalDate publishDate;
    @TableField("reading_time")
    private Integer readingTime;
    private Integer status;
}
