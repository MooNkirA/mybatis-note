-- MyBatis-Plus 示例数据脚本
create database mybatis_plus_sample_db default character set utf8;
use mybatis_plus_sample_db;

-- 数据库 Schema 脚本
DROP TABLE IF EXISTS user;

CREATE TABLE user
(
    id    BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    name  VARCHAR(30) NULL DEFAULT NULL COMMENT '姓名',
    age   INT(11) NULL DEFAULT NULL COMMENT '年龄',
    email VARCHAR(50) NULL DEFAULT NULL COMMENT '邮箱',
    PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- 数据库 Data 脚本
DELETE
FROM user;

INSERT INTO user (id, name, age, email)
VALUES (1, 'Jone', 18, 'test1@moon.com'),
       (2, 'Jack', 20, 'test2@moon.com'),
       (3, 'Tom', 28, 'test3@moon.com'),
       (4, 'Sandy', 21, 'test4@moon.com'),
       (5, 'Billie', 24, 'test5@moon.com');