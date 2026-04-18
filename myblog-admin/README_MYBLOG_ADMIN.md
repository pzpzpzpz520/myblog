# MyBlog Admin (RuoYi-Cloud)

该文档用于本仓库的 RuoYi 管理端本地联调，目标是与现有博客前台链路并行运行：

- 博客前台链路：`myblog-frontend + blog-gateway + blog-service-content`
- 后台管理链路：`ruoyi-ui + ruoyi-gateway + ruoyi-auth + ruoyi-system + ruoyi-blog-admin`

## 1. 启动本地依赖

在仓库根目录执行：

```bash
cd myblog-admin
docker compose -f docker-compose.local.yml up -d
```

会启动：

- MySQL（3306）
- Redis（6379）
- Nacos（8848）

并自动创建 3 个库：

- `ruoyi`（权限/菜单/系统）
- `myblog`（博客业务数据）
- `ry-config`（Nacos 配置数据）

## 2. 初始化数据库

在仓库根目录执行以下 SQL 导入：

```bash
mysql -h127.0.0.1 -uroot -ppassword ruoyi < myblog-admin/sql/ry_20260417.sql
mysql -h127.0.0.1 -uroot -ppassword ry-config < myblog-admin/sql/ry_config_20260310.sql
mysql -h127.0.0.1 -uroot -ppassword ry-config < myblog-admin/sql/myblog_admin_nacos_seed.sql
mysql -h127.0.0.1 -uroot -ppassword ruoyi < myblog-admin/sql/myblog_admin_menu_seed.sql
mysql -h127.0.0.1 -uroot -ppassword myblog < myblog-backend/blog-service-content/src/main/resources/schema.sql
mysql -h127.0.0.1 -uroot -ppassword myblog < myblog-backend/blog-service-content/src/main/resources/data.sql
```

## 3. 启动服务

后端（可在 IDE 分别启动）：

- `com.ruoyi.auth.RuoYiAuthApplication`
- `com.ruoyi.gateway.RuoYiGatewayApplication`
- `com.ruoyi.system.RuoYiSystemApplication`
- `com.ruoyi.blogadmin.RuoYiBlogAdminApplication`

前端管理端：

```bash
cd myblog-admin/ruoyi-ui
npm install
npm run dev
```

默认访问：

- 管理端 UI: `http://localhost:80`（若使用本仓库默认 nginx）或 `http://localhost:8080`（本地 dev 代理方式）
- 网关：`http://localhost:8080`
- 博客管理 API（经网关）：`http://localhost:8080/blog-admin/**`

## 4. 已接入后台管理能力

已新增 RuoYi 博客管理模块 `ruoyi-modules-blog-admin`，支持：

- 文章：列表/详情/新增/编辑/发布状态切换/删除
- 分类：列表/新增/编辑/删除（删除前做文章引用保护）
- 标签：列表/新增/编辑/删除（删除前做关联引用保护）
- 站点资料：查看/更新（`user_profile`）

权限点示例：

- `blog:article:list`
- `blog:article:edit`
- `blog:article:publish`
- `blog:category:remove`
- `blog:tag:remove`
- `blog:profile:edit`
