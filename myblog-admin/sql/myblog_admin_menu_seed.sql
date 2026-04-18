SET NAMES utf8mb4;

USE `ruoyi`;

-- 博客管理目录
INSERT INTO sys_menu
(menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES
(2000, '博客管理', 0, 5, 'blog', NULL, '', '', 1, 0, 'M', '0', '0', '', 'guide', 'admin', NOW(), '', NULL, '博客后台管理目录')
ON DUPLICATE KEY UPDATE
menu_name = VALUES(menu_name),
parent_id = VALUES(parent_id),
order_num = VALUES(order_num),
path = VALUES(path),
component = VALUES(component),
menu_type = VALUES(menu_type),
visible = VALUES(visible),
status = VALUES(status),
icon = VALUES(icon),
remark = VALUES(remark);

-- 菜单页面
INSERT INTO sys_menu
(menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES
(2001, '文章管理', 2000, 1, 'article', 'blog/article/index', '', '', 1, 0, 'C', '0', '0', 'blog:article:list', 'form', 'admin', NOW(), '', NULL, '文章管理菜单'),
(2002, '分类管理', 2000, 2, 'category', 'blog/category/index', '', '', 1, 0, 'C', '0', '0', 'blog:category:list', 'tree', 'admin', NOW(), '', NULL, '分类管理菜单'),
(2003, '标签管理', 2000, 3, 'tag', 'blog/tag/index', '', '', 1, 0, 'C', '0', '0', 'blog:tag:list', 'dict', 'admin', NOW(), '', NULL, '标签管理菜单'),
(2004, '站点资料', 2000, 4, 'profile', 'blog/profile/index', '', '', 1, 0, 'C', '0', '0', 'blog:profile:query', 'user', 'admin', NOW(), '', NULL, '站点资料菜单')
ON DUPLICATE KEY UPDATE
menu_name = VALUES(menu_name),
parent_id = VALUES(parent_id),
order_num = VALUES(order_num),
path = VALUES(path),
component = VALUES(component),
menu_type = VALUES(menu_type),
visible = VALUES(visible),
status = VALUES(status),
perms = VALUES(perms),
icon = VALUES(icon),
remark = VALUES(remark);

-- 文章按钮权限
INSERT INTO sys_menu
(menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES
(2100, '文章查询', 2001, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'blog:article:query', '#', 'admin', NOW(), '', NULL, ''),
(2101, '文章新增', 2001, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'blog:article:add', '#', 'admin', NOW(), '', NULL, ''),
(2102, '文章修改', 2001, 3, '', '', '', '', 1, 0, 'F', '0', '0', 'blog:article:edit', '#', 'admin', NOW(), '', NULL, ''),
(2103, '文章删除', 2001, 4, '', '', '', '', 1, 0, 'F', '0', '0', 'blog:article:remove', '#', 'admin', NOW(), '', NULL, ''),
(2104, '文章发布', 2001, 5, '', '', '', '', 1, 0, 'F', '0', '0', 'blog:article:publish', '#', 'admin', NOW(), '', NULL, '')
ON DUPLICATE KEY UPDATE
menu_name = VALUES(menu_name),
perms = VALUES(perms),
parent_id = VALUES(parent_id),
order_num = VALUES(order_num),
menu_type = VALUES(menu_type),
status = VALUES(status);

-- 分类按钮权限
INSERT INTO sys_menu
(menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES
(2200, '分类查询', 2002, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'blog:category:query', '#', 'admin', NOW(), '', NULL, ''),
(2201, '分类新增', 2002, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'blog:category:add', '#', 'admin', NOW(), '', NULL, ''),
(2202, '分类修改', 2002, 3, '', '', '', '', 1, 0, 'F', '0', '0', 'blog:category:edit', '#', 'admin', NOW(), '', NULL, ''),
(2203, '分类删除', 2002, 4, '', '', '', '', 1, 0, 'F', '0', '0', 'blog:category:remove', '#', 'admin', NOW(), '', NULL, '')
ON DUPLICATE KEY UPDATE
menu_name = VALUES(menu_name),
perms = VALUES(perms),
parent_id = VALUES(parent_id),
order_num = VALUES(order_num),
menu_type = VALUES(menu_type),
status = VALUES(status);

-- 标签按钮权限
INSERT INTO sys_menu
(menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES
(2300, '标签查询', 2003, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'blog:tag:query', '#', 'admin', NOW(), '', NULL, ''),
(2301, '标签新增', 2003, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'blog:tag:add', '#', 'admin', NOW(), '', NULL, ''),
(2302, '标签修改', 2003, 3, '', '', '', '', 1, 0, 'F', '0', '0', 'blog:tag:edit', '#', 'admin', NOW(), '', NULL, ''),
(2303, '标签删除', 2003, 4, '', '', '', '', 1, 0, 'F', '0', '0', 'blog:tag:remove', '#', 'admin', NOW(), '', NULL, '')
ON DUPLICATE KEY UPDATE
menu_name = VALUES(menu_name),
perms = VALUES(perms),
parent_id = VALUES(parent_id),
order_num = VALUES(order_num),
menu_type = VALUES(menu_type),
status = VALUES(status);

-- 站点资料按钮权限
INSERT INTO sys_menu
(menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES
(2400, '资料查询', 2004, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'blog:profile:query', '#', 'admin', NOW(), '', NULL, ''),
(2401, '资料修改', 2004, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'blog:profile:edit', '#', 'admin', NOW(), '', NULL, '')
ON DUPLICATE KEY UPDATE
menu_name = VALUES(menu_name),
perms = VALUES(perms),
parent_id = VALUES(parent_id),
order_num = VALUES(order_num),
menu_type = VALUES(menu_type),
status = VALUES(status);

-- 角色授权（超级管理员 + 普通角色）
INSERT IGNORE INTO sys_role_menu(role_id, menu_id)
VALUES
(1, 2000), (1, 2001), (1, 2002), (1, 2003), (1, 2004),
(1, 2100), (1, 2101), (1, 2102), (1, 2103), (1, 2104),
(1, 2200), (1, 2201), (1, 2202), (1, 2203),
(1, 2300), (1, 2301), (1, 2302), (1, 2303),
(1, 2400), (1, 2401),
(2, 2000), (2, 2001), (2, 2002), (2, 2003), (2, 2004),
(2, 2100), (2, 2101), (2, 2102), (2, 2103), (2, 2104),
(2, 2200), (2, 2201), (2, 2202), (2, 2203),
(2, 2300), (2, 2301), (2, 2302), (2, 2303),
(2, 2400), (2, 2401);
