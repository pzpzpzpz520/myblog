package com.zzq.blog.api.model.vo;

import lombok.Data;

import java.util.List;

@Data
public class UserProfileVO {

    private String name;
    private String title;
    private String university;
    private String major;
    private String location;
    private String email;
    private String bio;
    private List<String> focusAreas;
    private List<String> highlights;
}
