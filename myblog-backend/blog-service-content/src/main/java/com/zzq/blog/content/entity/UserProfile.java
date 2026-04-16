package com.zzq.blog.content.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("user_profile")
public class UserProfile {

    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private String title;
    private String university;
    private String major;
    private String location;
    private String email;
    private String bio;
    @TableField("focus_areas")
    private String focusAreas;
    private String highlights;
}
