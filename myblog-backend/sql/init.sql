CREATE DATABASE IF NOT EXISTS myblog DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE myblog;

SOURCE ./blog-service-content/src/main/resources/schema.sql;
SOURCE ./blog-service-content/src/main/resources/data.sql;
