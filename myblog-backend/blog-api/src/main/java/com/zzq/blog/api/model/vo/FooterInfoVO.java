package com.zzq.blog.api.model.vo;

import lombok.Data;

import java.util.List;

@Data
public class FooterInfoVO {

    private String slogan;
    private String signature;
    private String email;
    private String location;
    private List<NavigationItemVO> links;
}
