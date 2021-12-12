-- MyBatis-Plus 示例数据脚本
create
database mybatis_plus_sample_db default character set utf8;
use
mybatis_plus_sample_db;

-- 数据库 Schema 脚本
DROP TABLE IF EXISTS user;

CREATE TABLE user
(
    id      BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    name    VARCHAR(30) NULL DEFAULT NULL COMMENT '姓名',
    age     INT(11) NULL DEFAULT NULL COMMENT '年龄',
    email   VARCHAR(50) NULL DEFAULT NULL COMMENT '邮箱',
    role_id BIGINT (20) NOT NULL COMMENT '角色ID',
    PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS role;

CREATE TABLE role
(
    id            BIGINT (20) NOT NULL COMMENT '主键ID',
    role_name     VARCHAR(30) NULL DEFAULT NULL COMMENT '角色名',
    role_describe VARCHAR(30) NULL DEFAULT NULL COMMENT '角色描述',
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS goods;

CREATE TABLE goods
(
    id      BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    name    VARCHAR(30) NULL DEFAULT NULL COMMENT '商品名称',
    version INT(10) NULL DEFAULT 1 COMMENT '版本',
    deleted INT(10) NOT NULL DEFAULT 0 COMMENT '是否删除。1-删除 0-未删除',
    PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
