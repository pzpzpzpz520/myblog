package com.zzq.blog.api.model.vo;

import lombok.Data;

@Data
public class NavigationItemVO {

    private String label;
    private String path;
    private String description;
    private Boolean external;
}
