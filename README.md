# MyBlog

一个基于 `Vue 3 + TypeScript + Spring Cloud` 的前后端分离个人博客基础工程，适合持续扩展为个人品牌站、技术博客和后续管理后台。

## 目录结构

```text
myblog/
├── myblog-frontend   # Vue 3 博客前台
└── myblog-backend    # Spring Cloud 轻量微服务后端
```

## 快速启动

### 1. 启动数据库

执行 [`myblog-backend/sql/init.sql`](./myblog-backend/sql/init.sql) 初始化数据库，默认库名为 `myblog`。

### 2. 启动后端

```bash
cd myblog-backend
./mvnw -pl blog-service-content spring-boot:run
./mvnw -pl blog-gateway spring-boot:run
```

默认端口：

- 网关：`8080`
- 内容服务：`8081`

### 3. 启动前端

```bash
cd myblog-frontend
npm install
npm run dev
```

前端默认端口为 `5173`，开发环境通过 Vite 代理把 `/api` 请求转发到 `http://localhost:8080`。

## 已实现内容

- 个人主页、博客列表、博客详情、关于页
- 文章、分类、标签、首页聚合接口
- Git 管理基础配置
- 后端管理接口命名空间预留
- 可继续扩展鉴权、评论、搜索、后台管理
