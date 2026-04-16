CREATE TABLE IF NOT EXISTS user_profile (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(64) NOT NULL,
    title VARCHAR(128) NOT NULL,
    university VARCHAR(128) NOT NULL,
    major VARCHAR(128) NOT NULL,
    location VARCHAR(64),
    email VARCHAR(128),
    bio VARCHAR(255),
    focus_areas VARCHAR(255),
    highlights VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS category (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(64) NOT NULL
);

CREATE TABLE IF NOT EXISTS tag (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(64) NOT NULL
);

CREATE TABLE IF NOT EXISTS article (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(128) NOT NULL,
    summary VARCHAR(255) NOT NULL,
    content_markdown TEXT NOT NULL,
    cover_image VARCHAR(255),
    category_id BIGINT NOT NULL,
    publish_date DATE NOT NULL,
    reading_time INT DEFAULT 5,
    status TINYINT DEFAULT 1
);

CREATE TABLE IF NOT EXISTS article_tag (
    article_id BIGINT NOT NULL,
    tag_id BIGINT NOT NULL,
    PRIMARY KEY (article_id, tag_id)
);

CREATE TABLE IF NOT EXISTS site_module (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    page VARCHAR(32) NOT NULL,
    module_key VARCHAR(64) NOT NULL,
    title VARCHAR(128) NOT NULL,
    module_type VARCHAR(32) NOT NULL,
    enabled TINYINT DEFAULT 1,
    sort_order INT DEFAULT 0,
    payload_json TEXT,
    UNIQUE KEY uk_site_module_page_key (page, module_key)
);
