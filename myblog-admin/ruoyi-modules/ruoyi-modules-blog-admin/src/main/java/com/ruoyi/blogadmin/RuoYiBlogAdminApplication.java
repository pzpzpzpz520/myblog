package com.ruoyi.blogadmin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.ruoyi.common.security.annotation.EnableCustomConfig;
import com.ruoyi.common.security.annotation.EnableRyFeignClients;

/**
 * 博客管理模块
 */
@EnableCustomConfig
@EnableRyFeignClients
@SpringBootApplication
public class RuoYiBlogAdminApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(RuoYiBlogAdminApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  博客管理模块启动成功   ლ(´ڡ`ლ)ﾞ");
    }
}
