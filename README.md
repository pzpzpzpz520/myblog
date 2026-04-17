# MyBlog

一个基于 `Vue 3 + TypeScript + Spring Cloud` 的前后端分离个人博客工程，当前已经具备：

- 前台博客展示
- MySQL 驱动的站点内容
- `/api/admin/**` 管理接口预留
- Docker 化生产部署基础
- GitHub Actions 自动部署工作流
- Jenkins 备选流水线模板

## 目录结构

```text
myblog/
├── myblog-frontend   # Vue 3 博客前台
├── myblog-backend    # Spring Cloud 轻量微服务后端
├── docker-compose.prod.yml
├── .github/workflows/deploy.yml
└── scripts/deploy-prod.sh
```

## 本地开发

### 1. 初始化数据库

在 MySQL 中执行：

```sql
CREATE DATABASE IF NOT EXISTS myblog DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE myblog;
SOURCE /Users/yourmac/Documents/CODE/backend/myblog/myblog-backend/blog-service-content/src/main/resources/schema.sql;
SOURCE /Users/yourmac/Documents/CODE/backend/myblog/myblog-backend/blog-service-content/src/main/resources/data.sql;
SOURCE /Users/yourmac/Documents/CODE/backend/myblog/myblog-backend/blog-service-content/src/main/resources/schema_comment.sql;
```

### 2. 启动后端

内容服务：

```bash
cd myblog-backend
./mvnw -f blog-service-content/pom.xml spring-boot:run
```

网关：

```bash
cd myblog-backend
./mvnw -f blog-gateway/pom.xml spring-boot:run
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

## 公网部署原理

推荐链路：

`GitHub Push -> GitHub Actions -> 远程服务器 -> docker compose up -d --build`

运行结构：

`Browser -> Nginx(frontend container) -> /api -> blog-gateway -> blog-service-content -> MySQL`

核心原理：

- `GitHub Actions` 负责持续集成和自动触发部署
- `docker-compose.prod.yml` 负责把前端、网关、内容服务、MySQL 编排在一起
- `Nginx` 托管前端静态资源，并反向代理 `/api`
- `Spring Cloud Gateway` 负责统一 API 入口
- `MySQL` 负责站点资料、文章、首页模块等动态内容

如果你不想买云服务器，当前仓库也已经补好了 `Railway` 部署支持。Railway 方案的原理是：

`GitHub Repo -> Railway 自动构建 -> Railway 服务编排 -> 公网域名`

适用于当前项目的推荐拆分：

- `myblog-frontend`：公开服务，提供页面和 `/api` 反向代理
- `blog-gateway`：私有服务，统一 API 网关
- `blog-service-content`：私有服务，连接 MySQL 并提供内容接口
- `MySQL`：Railway 数据库服务

## 生产部署文件

- 前端镜像：`myblog-frontend/Dockerfile`
- 内容服务镜像：`myblog-backend/blog-service-content/Dockerfile`
- 网关镜像：`myblog-backend/blog-gateway/Dockerfile`
- 前端 Nginx 配置：`myblog-frontend/nginx/default.conf`
- 生产编排：`docker-compose.prod.yml`
- 部署脚本：`scripts/deploy-prod.sh`
- GitHub Actions：`.github/workflows/deploy.yml`
- Jenkins 备选：`Jenkinsfile`
- Railway 前端配置：`myblog-frontend/railway.toml`
- Railway 网关配置：`myblog-backend/blog-gateway/railway.toml`
- Railway 内容服务配置：`myblog-backend/blog-service-content/railway.toml`
- Railway 专用镜像：
  - `myblog-frontend/Railway.Dockerfile`
  - `myblog-backend/blog-gateway/Railway.Dockerfile`
  - `myblog-backend/blog-service-content/Railway.Dockerfile`

## 首次服务器部署

### 1. 服务器准备

至少准备这些基础设施：

- 一台 Linux 云服务器
- Docker
- Docker Compose Plugin
- Git

### 2. 拉取代码

```bash
git clone <your-repo-url> /opt/myblog
cd /opt/myblog
```

### 3. 配置生产环境变量

复制环境变量模板：

```bash
cp .env.prod.example .env
```

然后修改 `.env`：

```env
MYSQL_ROOT_PASSWORD=your_strong_password
MYSQL_DATABASE=myblog
MYSQL_USERNAME=root
MYSQL_PASSWORD=your_strong_password
FRONTEND_PORT=80
TZ=Asia/Shanghai
LOGGING_LEVEL_ROOT=info
LOGGING_LEVEL_APP=info
LOGGING_LEVEL_HIKARI=warn
```

### 4. 手工部署一次

```bash
chmod +x scripts/deploy-prod.sh
./scripts/deploy-prod.sh
```

部署成功后访问：

- `http://<你的服务器IP>`

## GitHub Actions 自动部署

工作流文件在：

- `.github/workflows/deploy.yml`

触发方式：

- push 到 `main`
- 手动点击 `workflow_dispatch`

### 需要配置的 GitHub Secrets

- `DEPLOY_HOST`：服务器 IP 或域名
- `DEPLOY_USER`：服务器用户名
- `DEPLOY_SSH_KEY`：用于登录服务器的私钥
- `DEPLOY_PORT`：SSH 端口，通常是 `22`
- `DEPLOY_PATH`：项目在服务器上的路径，例如 `/opt/myblog`

### 工作流做了什么

1. 检出代码
2. 跑前端测试和构建
3. 跑后端测试
4. 通过 SSH 登录服务器
5. `git fetch + git reset`
6. 执行 `scripts/deploy-prod.sh`

这是一种“仓库驱动 + 服务器本机构建”的 CD 方式，适合当前项目体量，部署链路简单，便于后续升级到镜像仓库模式。

## Railway 部署

### 1. 为什么默认推荐 Railway

因为你当前项目依赖：

- `Spring Boot / Spring Cloud`
- `MySQL`
- 多服务拆分
- GitHub 仓库自动触发部署

Railway 对这几个点更顺手，尤其适合先不上自购服务器、直接把项目公开上线。

### 2. Railway 上的服务拆分方式

创建同一个 Railway Project，包含 4 个服务：

1. `mysql`
2. `blog-service-content`
3. `blog-gateway`
4. `myblog-frontend`

### 3. GitHub 连接方式

在 Railway 中把当前 GitHub 仓库接入，然后为每个服务分别绑定：

- `blog-service-content`
  - Root Directory: `/`
  - Config File Path: `/myblog-backend/blog-service-content/railway.toml`
- `blog-gateway`
  - Root Directory: `/`
  - Config File Path: `/myblog-backend/blog-gateway/railway.toml`
- `myblog-frontend`
  - Root Directory: `/`
  - Config File Path: `/myblog-frontend/railway.toml`

这些配置文件已经在仓库里准备好，作用是：

- 指定 Dockerfile 路径
- 指定健康检查路径
- 指定部署重试策略
- 限制改动触发范围

### 4. Railway 服务变量

#### `blog-service-content`

配置：

- `MYSQL_HOST`
- `MYSQL_PORT`
- `MYSQL_DB`
- `MYSQL_USERNAME`
- `MYSQL_PASSWORD`
- `LOGGING_LEVEL_ROOT=info`
- `LOGGING_LEVEL_APP=info`
- `LOGGING_LEVEL_HIKARI=warn`

其中数据库相关变量建议直接引用 Railway MySQL 服务提供的变量。

#### `blog-gateway`

配置：

- `CONTENT_SERVICE_URL=http://blog-service-content.railway.internal:8081`
- `LOGGING_LEVEL_ROOT=info`
- `LOGGING_LEVEL_APP=info`

#### `myblog-frontend`

配置：

- `API_UPSTREAM=http://blog-gateway.railway.internal:8080`
- `PORT=80`

原理：

- 前端 Nginx 会把浏览器访问的 `/api` 转发到 `API_UPSTREAM`
- 网关再转到内容服务
- 内容服务再查 MySQL

### 5. 公网入口怎么开

只给 `myblog-frontend` 打开 Public Networking。

不要直接公开：

- `blog-gateway`
- `blog-service-content`
- `mysql`

这样更符合生产安全边界：

- 浏览器只看到前端域名
- 后端服务留在内部网络
- `/api` 由前端容器统一代理

### 6. 自动部署怎么工作

Railway 连接 GitHub 后，默认可以配置为：

- push 到 `main`
- 自动重新构建对应服务

这相当于“GitHub 事件触发 Railway 构建”，你不需要自己再写 SSH 部署脚本。

### 7. 数据初始化怎么处理

当前项目已经准备了：

- `schema.sql`
- `data.sql`
- `schema_comment.sql`

Railway 上如果你使用独立 MySQL 服务，建议首次手工导入这三份脚本；后续再逐步升级到 Flyway/Liquibase。

## Jenkins 方案原理

`Jenkinsfile` 已经提供了一个备选流水线模板，适合未来迁移到自建 CI/CD 平台。

原理与 GitHub Actions 相同，只是执行平台换成 Jenkins：

1. Jenkins 拉取代码
2. Jenkins 跑前端测试/构建
3. Jenkins 跑后端测试
4. Jenkins 通过 SSH 登录服务器
5. 执行部署脚本

如果未来你需要多项目统一流水线、企业内网部署、自定义节点和权限体系，Jenkins 会更适合。

## 当前实现内容

- 个人主页、博客列表、博客详情、关于页
- 文章、分类、标签、首页聚合接口
- 站点模块配置表 `site_module`
- 后端管理接口命名空间预留
- MySQL 初始化与字段注释脚本
- Docker 生产部署基础设施
- GitHub Actions 自动部署工作流
- Jenkins 流水线模板
