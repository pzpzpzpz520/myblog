SET NAMES utf8mb4;

USE `ry-config`;

-- 网关配置（加入 /blog-admin/** 路由）
SET @gateway_config = 'spring:
  data:
    redis:
      host: localhost
      port: 6379
      password:
  cloud:
    gateway:
      server:
        webflux:
          discovery:
            locator:
              lowerCaseServiceId: true
              enabled: true
          routes:
            - id: ruoyi-auth
              uri: lb://ruoyi-auth
              predicates:
                - Path=/auth/**
              filters:
                - CacheRequestBody
                - ValidateCodeFilter
                - StripPrefix=1
            - id: ruoyi-gen
              uri: lb://ruoyi-gen
              predicates:
                - Path=/code/**
              filters:
                - StripPrefix=1
            - id: ruoyi-job
              uri: lb://ruoyi-job
              predicates:
                - Path=/schedule/**
              filters:
                - StripPrefix=1
            - id: ruoyi-system
              uri: lb://ruoyi-system
              predicates:
                - Path=/system/**
              filters:
                - StripPrefix=1
            - id: ruoyi-file
              uri: lb://ruoyi-file
              predicates:
                - Path=/file/**
              filters:
                - StripPrefix=1
            - id: ruoyi-blog-admin
              uri: lb://ruoyi-blog-admin
              predicates:
                - Path=/blog-admin/**
              filters:
                - StripPrefix=1

security:
  captcha:
    enabled: true
    type: math
  xss:
    enabled: true
    excludeUrls:
      - /system/notice
  ignore:
    whites:
      - /auth/logout
      - /auth/login
      - /auth/register
      - /*/v2/api-docs
      - /*/v3/api-docs
      - /csrf

springdoc:
  webjars:
    prefix:
';

INSERT INTO config_info
(
    data_id, group_id, content, md5, gmt_create, gmt_modified, tenant_id, c_desc, type, encrypted_data_key
)
VALUES
(
    'ruoyi-gateway-dev.yml', 'DEFAULT_GROUP', @gateway_config, MD5(@gateway_config), NOW(), NOW(), '', '网关模块', 'yaml', ''
)
ON DUPLICATE KEY UPDATE
    content = VALUES(content),
    md5 = VALUES(md5),
    gmt_modified = NOW(),
    c_desc = VALUES(c_desc),
    type = VALUES(type);

-- 博客管理模块配置（连接 myblog 库）
SET @blog_admin_config = 'spring:
  data:
    redis:
      host: localhost
      port: 6379
      password:
  datasource:
    dynamic:
      primary: master
      datasource:
        master:
          driver-class-name: com.mysql.cj.jdbc.Driver
          url: jdbc:mysql://localhost:3306/myblog?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8
          username: root
          password: password

mybatis:
  typeAliasesPackage: com.ruoyi.blogadmin.domain
  mapperLocations: classpath:mapper/**/*.xml

springdoc:
  gatewayUrl: http://localhost:8080/${spring.application.name}
  api-docs:
    enabled: true
  info:
    title: 博客管理模块接口文档
    description: 博客后台管理接口
    contact:
      name: myblog
      url: https://myblog.local
';

INSERT INTO config_info
(
    data_id, group_id, content, md5, gmt_create, gmt_modified, tenant_id, c_desc, type, encrypted_data_key
)
VALUES
(
    'ruoyi-blog-admin-dev.yml', 'DEFAULT_GROUP', @blog_admin_config, MD5(@blog_admin_config), NOW(), NOW(), '', '博客管理模块', 'yaml', ''
)
ON DUPLICATE KEY UPDATE
    content = VALUES(content),
    md5 = VALUES(md5),
    gmt_modified = NOW(),
    c_desc = VALUES(c_desc),
    type = VALUES(type);
