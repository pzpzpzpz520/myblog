package com.zzq.blog.content.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("article_tag")
public class ArticleTag {

    private Long articleId;
    private Long tagId;
}
