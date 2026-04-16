package com.zzq.blog.content;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.zzq.blog")
@MapperScan("com.zzq.blog.content.mapper")
public class BlogContentApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogContentApplication.class, args);
    }
}
