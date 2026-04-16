package com.zzq.blog.content.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("site_module")
public class SiteModule {

    @TableId(type = IdType.AUTO)
    private Long id;
    private String page;
    @TableField("module_key")
    private String moduleKey;
    private String title;
    @TableField("module_type")
    private String moduleType;
    private Integer enabled;
    @TableField("sort_order")
    private Integer sortOrder;
    @TableField("payload_json")
    private String payloadJson;
}
