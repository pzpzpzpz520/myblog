INSERT INTO user_profile (id, name, title, university, major, location, email, bio, focus_areas, highlights)
VALUES (1, '张子强', '计算机科学与技术背景 · Java 后端 / 运维工程化', '辽宁师范大学', '计算机科学与技术', '中国 · 河北石家庄', 'wy1811657977@163.com',
        '聚焦 Java 后端、系统稳定性与工程化实践，正在把项目复盘、成长记录和技术积累沉淀为长期可演进的个人博客。',
        'Java 后端,系统稳定性,部署运维,工程化实践,Vue 3 + TypeScript',
        'GPA 3.6 / 专业前 20%,软通动力开发运维工程师,企业核心系统项目经验,CET-4 / CET-6')
ON DUPLICATE KEY UPDATE name = VALUES(name);

INSERT INTO category (id, name)
VALUES (1, '成长记录'),
       (2, '后端工程'),
       (3, '项目复盘'),
       (4, '部署运维')
ON DUPLICATE KEY UPDATE name = VALUES(name);

INSERT INTO tag (id, name)
VALUES (1, 'Java'),
       (2, 'Spring Cloud'),
       (3, 'Vue 3'),
       (4, 'DevOps'),
       (5, '系统设计'),
       (6, '成长复盘')
ON DUPLICATE KEY UPDATE name = VALUES(name);

INSERT INTO article (id, title, summary, content_markdown, cover_image, category_id, publish_date, reading_time, status)
VALUES
    (1, '从开发运维视角重构我的个人博客', '这次博客重构不只是换一套页面，而是把内容、模块和未来后台能力一起铺好。',
     '# 从开发运维视角重构我的个人博客\n\n这个博客将长期记录我的技术积累、项目复盘和成长路径。相比只搭一个静态首页，我更希望把它做成一个真正可演进的内容系统。\n\n## 为什么从前后端分离开始\n\n- 前台展示和后台管理天然关注点不同\n- 文章、分类、标签和站点模块都需要动态维护能力\n- 后续接入后台时，不需要重写现有前台结构\n\n## 这次重构关注什么\n\n- 统一前台展示的数据来源\n- 为后台管理接口预留命名空间\n- 用真实经历替换占位内容\n- 把页面体验做得更完整、更有层次\n',
     'https://images.unsplash.com/photo-1516321318423-f06f85e504b3?auto=format&fit=crop&w=1200&q=80', 2, '2026-04-10', 6, 1),
    (2, '测试环境稳定性保障，我在项目里做了什么', '从服务器、中间件、日志分析到版本协同，稳定性从来不是一句口号。',
     '# 测试环境稳定性保障，我在项目里做了什么\n\n在开发运维岗位上，我很早就意识到“代码完成”并不等于“系统可用”。真正影响交付质量的，往往是部署链路、监控能力和问题定位效率。\n\n## 我主要负责的事情\n\n- 测试环境服务器和中间件部署\n- 应用发布与版本协同\n- 日志分析和风险排查\n- 通过流程规范降低环境问题的重复发生\n\n## 这些经历带来的变化\n\n它让我在写代码时，会更关注配置、边界条件、日志信息和可追溯性，而不只盯着功能本身。\n',
     'https://images.unsplash.com/photo-1455390582262-044cdead277a?auto=format&fit=crop&w=1200&q=80', 4, '2026-04-12', 5, 1),
    (3, '企业核心系统项目里的模块设计与数据映射', '从外汇结算子系统开发经历出发，记录我对模块职责和数据映射的一些理解。',
     '# 企业核心系统项目里的模块设计与数据映射\n\n在集团财务公司核心系统项目中，我参与了外汇结算子系统板块的开发工作。项目采用传统企业级技术栈，但它对模块职责和数据一致性的要求非常高。\n\n## 这段经历让我更重视什么\n\n- 数据库设计和 ORM 映射要服务业务约束\n- 模块边界清晰，后续维护成本才可控\n- 系统稳定性不能和业务实现割裂来看\n\n## 之后在个人项目里如何吸收\n\n- 先做清楚接口边界，再写页面和控制器\n- 尽量让内容模型和展示模型解耦\n- 给未来管理系统预留足够清晰的扩展入口\n',
     'https://images.unsplash.com/photo-1498050108023-c5249f4df085?auto=format&fit=crop&w=1200&q=80', 3, '2026-04-15', 7, 1),
    (4, '接下来一年，我会怎么继续补齐后端工程能力', '在继续深挖 Java 后端的同时，把系统设计、稳定性和工程化实践真正串起来。',
     '# 接下来一年，我会怎么继续补齐后端工程能力\n\n我希望未来的成长不是碎片化学一点、记一点，而是围绕“能独立设计、实现、部署和维护一套系统”去补全能力。\n\n## 重点方向\n\n- Java 与 Spring 体系的继续深化\n- 微服务与系统边界拆分能力\n- 部署运维、监控和故障排查方法论\n- 用真实项目持续验证学习结果\n\n## 博客在这里的作用\n\n它既是记录工具，也是倒逼自己结构化表达和持续交付的方式。\n',
     'https://images.unsplash.com/photo-1522202176988-66273c2fd55f?auto=format&fit=crop&w=1200&q=80', 1, '2026-04-16', 6, 1)
ON DUPLICATE KEY UPDATE title = VALUES(title);

INSERT INTO article_tag (article_id, tag_id)
VALUES (1, 2),
       (1, 3),
       (1, 6),
       (2, 1),
       (2, 4),
       (3, 1),
       (3, 5),
       (4, 1),
       (4, 2),
       (4, 6)
ON DUPLICATE KEY UPDATE tag_id = VALUES(tag_id);

INSERT INTO site_module (id, page, module_key, title, module_type, enabled, sort_order, payload_json)
VALUES
    (1, 'global', 'main-navigation', '主导航', 'navigation', 1, 1,
     '{"items":[{"label":"首页","path":"/","description":"回到博客首页","external":false},{"label":"博客","path":"/blog","description":"查看全部文章","external":false},{"label":"关于","path":"/about","description":"查看个人经历","external":false}]}'),
    (2, 'global', 'site-footer', '页脚信息', 'footer', 1, 2,
     '{"slogan":"记录成长、沉淀方法、保持长期主义。","signature":"张子强 · Java 后端 / 运维工程化","email":"wy1811657977@163.com","location":"河北石家庄","links":[{"label":"博客文章","path":"/blog","description":"继续阅读","external":false},{"label":"关于我","path":"/about","description":"查看完整经历","external":false}]}'),
    (3, 'home', 'career-journey', '成长轨迹', 'timeline', 1, 1,
     '{"eyebrow":"ROADMAP","description":"从本科计算机科学与技术训练，到企业级系统的一线开发运维实践，再到为后续长期技术成长搭建自己的内容系统。","items":[{"period":"2020.10 - 2024.06","title":"辽宁师范大学","summary":"计算机科学与技术本科，GPA 3.6，专业排名前 20%。"},{"period":"2024.05 - 2025.10","title":"软通动力信息技术（集团）股份有限公司","summary":"担任开发运维工程师，负责需求分析、编码实现、部署与稳定性保障。"},{"period":"Now","title":"个人技术博客建设","summary":"把项目复盘、工程方法和成长记录做成可持续演进的内容系统。"}]}'),
    (4, 'home', 'ability-highlights', '能力亮点', 'feature-grid', 1, 2,
     '{"eyebrow":"STRENGTHS","description":"既关注编码实现，也重视部署链路、系统监控和版本协同，让项目在真实场景里稳定落地。","cards":[{"title":"后端实现","text":"具备 Java 企业项目开发经验，关注模块职责、ORM 映射与可维护性。"},{"title":"稳定性保障","text":"负责测试环境与中间件部署、日志分析和故障排查。"},{"title":"工程化意识","text":"持续把部署脚本、版本协同和流程规范整理成可复用的方法。"}]}'),
    (5, 'home', 'column-overview', '博客栏目', 'column-list', 1, 3,
     '{"eyebrow":"COLUMNS","columns":[{"name":"后端工程","description":"Java、Spring、模块设计、数据库与接口分层。"},{"name":"部署运维","description":"测试环境、日志排障、发布流程和系统稳定性复盘。"},{"name":"成长记录","description":"项目经验、读研准备、长期学习路径与阶段总结。"}]}'),
    (6, 'about', 'education', '教育背景', 'detail-list', 1, 1,
     '{"eyebrow":"EDUCATION","items":[{"label":"本科院校","value":"辽宁师范大学 · 计算机科学与技术"},{"label":"时间","value":"2020.10 - 2024.06"},{"label":"成绩","value":"GPA 3.6，专业排名前 20%"},{"label":"核心课程","value":"数据结构、计算机网络、操作系统、计算机组成原理"}]}'),
    (7, 'about', 'work-experience', '工作经历', 'experience-list', 1, 2,
     '{"eyebrow":"EXPERIENCE","items":[{"title":"软通动力信息技术（集团）股份有限公司 · 开发运维工程师","period":"2024.05 - 2025.10","points":["负责需求分析与编码实现，撰写相关技术文档，保证开发过程规范可追溯。","负责测试环境服务器、中间件与应用部署，保障测试环境可用性。","持续进行资源监控与日志分析，发现并排查潜在风险。","参与代码库版本管理和分支协同，降低迭代过程中的版本风险。"]}]}'),
    (8, 'about', 'skills', '技能与证书', 'badge-list', 1, 3,
     '{"eyebrow":"SKILLS","items":["Java / Spring 体系","Hibernate / Dubbo / RocketMQ / Zookeeper","Redis / Oracle / MongoDB / Nginx","Linux Shell / 部署脚本 / CI-CD 认知","CET-4 / CET-6"]}'),
    (9, 'about', 'projects', '项目经验', 'project-showcase', 1, 4,
     '{"eyebrow":"PROJECTS","projects":[{"name":"集团财务公司核心系统","summary":"服务于集团内部资金结算、账户管理与信贷业务的企业级金融管理系统。","stack":["EJB","Hibernate","Dubbo","RocketMQ","Redis","Oracle","Nginx"],"highlights":["负责外汇结算子系统板块开发，完成数据库设计、ORM 优化与后端业务实现。","参与生产和测试环境的高可用维护，结合日志与堆栈追踪定位并修复异常。","编写 Linux Shell 自动化部署与监控脚本，提升多节点环境发布效率。"]}]}')
ON DUPLICATE KEY UPDATE title = VALUES(title), module_type = VALUES(module_type), enabled = VALUES(enabled), sort_order = VALUES(sort_order), payload_json = VALUES(payload_json);
