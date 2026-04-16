package com.zzq.blog.api.model.vo;

import lombok.Data;

import java.util.Map;

@Data
public class SiteModuleVO {

    private Long id;
    private String page;
    private String key;
    private String title;
    private String moduleType;
    private Boolean enabled;
    private Integer sortOrder;
    private Map<String, Object> payload;
}
